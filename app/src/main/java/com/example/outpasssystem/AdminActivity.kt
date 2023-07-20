package com.example.outpasssystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outpasssystem.databinding.ActivityAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdminActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityAdminBinding
    private lateinit var OutpassList : ArrayList<AdminOutpassRV>
    private lateinit var outpassAdminAdapter: OutpassAdminAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        recyclerView = binding.rvAdmin
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        OutpassList = arrayListOf<AdminOutpassRV>()
        getOutpassData()


        binding.fabBackAdmin.setOnClickListener {
            mAuth.signOut()
            val intent= Intent(this@AdminActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun getOutpassData() {
        database = FirebaseDatabase.getInstance().getReference("Admin")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (outpass in snapshot.children){
                        val op = outpass.getValue(AdminOutpassRV::class.java)!!
                        OutpassList.add(op)
                    }
                    OutpassList.reverse()
                    recyclerView.adapter = OutpassAdminAdapter(OutpassList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onBackPressed() {
        mAuth.signOut()
        val intent = Intent(this@AdminActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}