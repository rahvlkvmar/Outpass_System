package com.example.outpasssystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.outpasssystem.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.btnCancelA.setOnClickListener {
            finish()
            overridePendingTransition(androidx.appcompat.R.anim.abc_slide_in_top,
                androidx.appcompat.R.anim.abc_slide_out_bottom)
        }

        binding.btnSubmitA.setOnClickListener {
            val firstlastname = binding.etNameA.text.toString()
            val rollnum = binding.etRollnoA.text.toString()
            val hostelnum = binding.etHostelA.text.toString()
            val roomnum = binding.etRoomA.text.toString()
            val phonenum = binding.etSelfcontactA.text.toString()
            val guardianname = binding.etParentnameA.text.toString()
            val guardiannum = binding.etParentcontactA.text.toString()
            val email = binding.etEmailA.text.toString()
            val password = binding.etPasswordA.text.toString()
            val confirmpass = binding.etConfirmPasswordA.text.toString()

            if (firstlastname.isBlank() || rollnum.isBlank() || hostelnum.isBlank()
                || roomnum.isBlank() || phonenum.isBlank() || guardianname.isBlank()
                ||guardiannum.isBlank() || email.isBlank() || password.isBlank()
                || confirmpass.isBlank()){
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
            else if (password != confirmpass){
                Toast.makeText(this, "Password and Confirm Password Fields do not match!", Toast.LENGTH_SHORT).show()
            }
            else{
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                    if (it.isSuccessful){
                        Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                        val uid = mAuth.currentUser?.uid!!
                        val user = User(firstlastname, rollnum,hostelnum,roomnum,phonenum,guardianname,guardiannum,email,uid, 1)
                        database.child(uid).setValue(user)
                        val intent = Intent(this@CreateAccountActivity, StudentActivity::class.java)
                        startActivity(intent)
                        finish()
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    else{
                        Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }

}