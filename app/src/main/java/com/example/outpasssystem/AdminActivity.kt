package com.example.outpasssystem

import android.content.Context
import android.content.Intent
import android.graphics.fonts.FontFamily
import android.graphics.fonts.FontStyle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outpasssystem.databinding.ActivityAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class AdminActivity(context: Context) : AppCompatActivity() {

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
        binding.tvEmptylist.visibility = View.VISIBLE

        OutpassList = arrayListOf<AdminOutpassRV>()
        getOutpassData()

        binding.fabBackAdmin.setOnClickListener {
            mAuth.signOut()
            val intent= Intent(this@AdminActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }


    }

    private fun getOutpassData() {
        database = FirebaseDatabase.getInstance().getReference("Admin")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    OutpassList.clear()
                    for (outpass in snapshot.children){
                        val op = outpass.getValue(AdminOutpassRV::class.java)!!
                        OutpassList.add(op)
                    }
                    if (OutpassList.isEmpty()){
                        binding.tvEmptylist.visibility = View.VISIBLE
                    }
                    else{
                        binding.tvEmptylist.visibility = View.INVISIBLE
                    }
                    OutpassList.reverse()
                    outpassAdminAdapter = OutpassAdminAdapter(OutpassList)
                    recyclerView.adapter = outpassAdminAdapter
                    outpassAdminAdapter.setOnItemClickListener(object: OutpassAdminAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val sNo = OutpassList[position].sno
                            val intent = Intent(this@AdminActivity, ShowActivity::class.java)
                            intent.putExtra("Outpass", sNo)
                            startActivity(intent)
                            finish()
                        }
                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


    override fun onBackPressed() {
        mAuth.signOut()
        val intent = Intent(this@AdminActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}