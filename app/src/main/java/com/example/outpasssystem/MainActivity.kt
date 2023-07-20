package com.example.outpasssystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.outpasssystem.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(androidx.appcompat.R.anim.abc_slide_in_bottom, androidx.appcompat.R.anim.abc_slide_out_top)
        }

        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            if (email.isBlank() || pass.isBlank()){
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this){
                    if (it.isSuccessful){
                        if (email == "admin@lnmiit.ac.in"){
                            val intent = Intent(this@MainActivity, AdminActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            val intent = Intent(this@MainActivity, StudentActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    else{
                        Toast.makeText(this, "Your Email or Password is incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun finish(){
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}