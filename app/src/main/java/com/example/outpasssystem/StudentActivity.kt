package com.example.outpasssystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outpasssystem.databinding.ActivityStudentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class StudentActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var dbref : DatabaseReference
    private lateinit var binding: ActivityStudentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var outpassList: ArrayList<OutpassStudentRV>
    private lateinit var outpassStudentAdapter: OutpassStudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        recyclerView = binding.rvStudent
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        outpassList = arrayListOf<OutpassStudentRV>()
        getUserData("Approved")

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@StudentActivity, ApplyActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.btnLogout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this@StudentActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.tvApproved.setOnClickListener {
            binding.tvApproved.setBackgroundResource(R.drawable.status_button_clicked)
            binding.tvPending.setBackgroundResource(R.drawable.status_background)
            binding.tvRejected.setBackgroundResource(R.drawable.status_background)
            getUserData("Approved")
        }

        binding.tvPending.setOnClickListener {
            binding.tvApproved.setBackgroundResource(R.drawable.status_background)
            binding.tvPending.setBackgroundResource(R.drawable.status_button_clicked)
            binding.tvRejected.setBackgroundResource(R.drawable.status_background)
            getUserData("Pending")
        }

        binding.tvRejected.setOnClickListener {
            binding.tvApproved.setBackgroundResource(R.drawable.status_background)
            binding.tvPending.setBackgroundResource(R.drawable.status_background)
            binding.tvRejected.setBackgroundResource(R.drawable.status_button_clicked)
            getUserData("Rejected")
        }

    }

    override fun onBackPressed() {
        mAuth.signOut()
        val intent = Intent(this@StudentActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun finish(){
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun getUserData(Status: String) {
        dbref = FirebaseDatabase.getInstance().getReference("Outpasses")
        val uid = mAuth.currentUser?.uid!!


        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    outpassList.clear()
                    if (snapshot.hasChild(uid)) {
                        val outpassSnapshot = snapshot.child(uid)
                        for (singleOutpass in outpassSnapshot.children) {
                            val op = singleOutpass.getValue(OutpassStudentRV::class.java)
                            if (op?.status == Status){
                                outpassList.add(op)
                            }
                        }
                    }
                    outpassList.reverse()
                    recyclerView.adapter = OutpassStudentAdapter(outpassList, Status)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}