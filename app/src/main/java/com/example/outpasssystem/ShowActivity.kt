package com.example.outpasssystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.outpasssystem.databinding.ActivityShowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowBinding
    private lateinit var database : DatabaseReference
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        val sno = intent.getStringExtra("Outpass")
        var uid: String? = null
        if (sno != null){

            database = FirebaseDatabase.getInstance().getReference("Admin")
            database.child(sno).get().addOnSuccessListener {
                uid = it.child("uid").value.toString()
                binding.tvShowName.text = it.child("name").value.toString()
                binding.tvShowRollNum.text = it.child("rollnum").value.toString()
                binding.tvShowHostel.text = it.child("hostel").value.toString()
                binding.tvShowRoom.text = it.child("roomNum").value.toString()
                binding.tvShowNum.text = it.child("contactNum").value.toString()
                binding.tvShowParent.text = it.child("parent").value.toString()
                binding.tvShowParentNum.text = it.child("parentNum").value.toString()
                binding.tvShowLeave.text = it.child("leave").value.toString()
                binding.tvShowArrive.text = it.child("arrive").value.toString()
                binding.tvShowTransport.text = it.child("transport").value.toString()
                binding.tvShowPurpose.text = it.child("purpose").value.toString()
            }

        }




        binding.fabAcceptShow.setOnClickListener {
            if (sno!=null && uid != null){
                //logic to delete outpass from "Admin", find outpass using uid and sno and change status to "Approved"

                database = FirebaseDatabase.getInstance().getReference("Admin")
                database.child(sno).removeValue()
                database = FirebaseDatabase.getInstance().getReference("Outpasses")
                val newState = mapOf<String, String>(
                    "status" to "Approved"
                )
                database.child(uid!!).child(sno).updateChildren(newState)


                Toast.makeText(this, "Outpass Status Updated Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ShowActivity, AdminActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Some error occurred. Please try again", Toast.LENGTH_SHORT).show()
            }
        }
//
        binding.fabRejectShow.setOnClickListener {
            if (sno!=null && uid != null){
                //logic to delete outpass from "Admin", find outpass using uid and sno and change status to "Approved"

                database = FirebaseDatabase.getInstance().getReference("Admin")
                database.child(sno).removeValue()
                database = FirebaseDatabase.getInstance().getReference("Outpasses")
                val newState = mapOf<String, String>(
                    "status" to "Rejected"
                )
                database.child(uid!!).child(sno).updateChildren(newState)


                Toast.makeText(this, "Outpass Status Updated Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ShowActivity, AdminActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Some error occurred. Please try again", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabBackShow.setOnClickListener {
            val intent = Intent(this@ShowActivity, AdminActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


}