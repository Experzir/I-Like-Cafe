package com.example.cafever2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cafever2.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Login Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        val login_loginBtn = findViewById<Button>(R.id.login_loginBtn)
        val login_emailEditText = findViewById<TextView>(R.id.login_emailEditText)
        val login_passwordEditText = findViewById<TextView>(R.id.login_passwordEditText)
        val login_createBtn = findViewById<Button>(R.id.login_createBtn)
        login_createBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        login_loginBtn.setOnClickListener {
            val email = login_emailEditText.text.toString().trim { it <= ' ' }
            val password = login_passwordEditText.text.toString().trim { it <= ' ' }
// ทําการตรวจสอบก่อนว่ามีข้อมูลหรือไม่ ก่อนที่จะไปตรวจสอบค่า
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter your password.", Toast.LENGTH_LONG).show()
                Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }
//ทําการตรวจสอบค่าที่กรอกกับค่าจาก Firebase Authentication
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful) { //กรณีที่ไม่ผ่านการตรวจสอบ
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
                } else { //กรณีที่อีเมลและรหัสถูกต้อง
                    Toast.makeText(this, "Sign in successfully!", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Sign in successfully!")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//}