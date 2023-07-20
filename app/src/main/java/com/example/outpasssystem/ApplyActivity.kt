package com.example.outpasssystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.outpasssystem.databinding.ActivityApplyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ApplyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplyBinding
    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()

        binding.fabBackSU.setOnClickListener {
            finish()
        }

        binding.btnSubmitSU.setOnClickListener {
            val leaveDT = binding.etLeaveSU.text.toString()
            val arriveDT = binding.etArriveSU.text.toString()
            val modeTranport = binding.etTransportSU.text.toString()
            val purposeOfVisit = binding.etPurposeSU.text.toString()
            if (leaveDT.isBlank() || arriveDT.isBlank() || modeTranport.isBlank() || purposeOfVisit.isBlank()){
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
            else{
                val uniqueID = mAuth.currentUser?.uid!!
                database = FirebaseDatabase.getInstance().getReference("Users")
                database.child("GlobalSNo").get().addOnSuccessListener { it1 ->
                    val sno = it1.getValue(Int::class.java)!!
                    database.child("GlobalSNo").setValue(sno+1)
                    database = FirebaseDatabase.getInstance().getReference("Outpasses")
                    val newOutpass = Outpass(leaveDT, arriveDT, modeTranport, purposeOfVisit, "Pending")
                    database.child(uniqueID).child(sno.toString()).setValue(newOutpass)

                    database = FirebaseDatabase.getInstance().getReference("Users")
                    database.child(uniqueID).get().addOnSuccessListener {
                        val cU = it.getValue(User::class.java)
                        database = FirebaseDatabase.getInstance().getReference("Admin")
                        //create an object and set all values required values for Admin branch
                        val adminOutpass  = AdminOutpassRV(sno, cU?.name, cU?.rollno, cU?.hostel,
                            cU?.roomno, cU?.phoneno, cU?.parentname, cU?.parentno, leaveDT,
                            arriveDT, modeTranport, purposeOfVisit, uniqueID)
                        database.child(sno.toString()).setValue(adminOutpass)
                    }

                    Toast.makeText(this, "Your request has been received!", Toast.LENGTH_SHORT).show()
                    finish()
                }


            }
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}