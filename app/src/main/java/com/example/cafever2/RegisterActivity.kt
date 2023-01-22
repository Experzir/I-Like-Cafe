package com.example.cafever2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Register Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null) {
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
            finish()
        }
        val register_registerBtn = findViewById<Button>(R.id.register_registerBtn)
        val register_emailEditText = findViewById<TextView>(R.id.register_emailEditText)
        val register_passwordEditText = findViewById<TextView>(R.id.register_passwordEditText)
        val register_signinBtn = findViewById<Button>(R.id.register_signinBtn)
        register_signinBtn.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
        }
        register_registerBtn.setOnClickListener {
            val email = register_emailEditText.text.toString().trim { it <= ' ' }
            val password = register_passwordEditText.text.toString().trim { it <= ' ' }
            //ทําการตรวจสอบก่อนว่ามีข้อมูลหรือไม่
            if (email.isEmpty()) {
                Toast.makeText(this,"Please enter your email address.", Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this,"Please enter your password.", Toast.LENGTH_LONG).show()
                Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }
//กรณีที่มีข้อมูล จะทําการตรวจสอบเงื่อนไขอื่น ๆ ก่อนทําการ create user
            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    //เพิ่มเงื่อนไข ตรวจสอบอะไร
                    if (password.length < 8) { // ตรวจสอบความยาวของ password
                        Toast.makeText(this,"Password must have minimum 8 characters.", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Enter password less than 8 characters.")
                    }
                    else if (!password.matches(".*[0-9].*".toRegex())){
                        Toast.makeText(this,"password must have minimum 1 number", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Please enter some number in password ")
                    }
                    else if (!password.matches(".*[a-z].*".toRegex())){
                        Toast.makeText(this,"password must have minimum 1 lowercase letters", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Please enter some lowercase letters")
                    }
                    else if (!password.matches(".*[A-Z].*".toRegex())){
                        Toast.makeText(this,"password must have minimum 1 uppercase letters", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Please enter some uppercase letters")
                    } else {
                        Toast.makeText(this, "Authentication  Failed: " +
                                task.exception!!.message, Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Authentication Failed: " + task.exception!!.message)
                    }
                } else {
                    Toast.makeText(this,"Create account successfully!", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Create account successfully!")
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}