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
                database.child(uniqueID).get().addOnSuccessListener {
                    val sno  = it.child("serialno").getValue(Int::class.java)!!
                    val newNo = sno + 1
                    val newVal = mapOf<String, Int>("serialno" to newNo)
                    database.child(uniqueID).updateChildren(newVal)
                    database = FirebaseDatabase.getInstance().getReference("Outpasses")
                    val newOutpass = Outpass(leaveDT, arriveDT, modeTranport, purposeOfVisit, "Pending")
                    database.child(uniqueID).child(sno.toString()).setValue(newOutpass)
                    //create a global variable serial number and a new branch for AdminOutpass which stores every
                    //outpass that is generated and deletes every outpass that is approved or rejected
                }
                Toast.makeText(this, "Your request has been received!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}