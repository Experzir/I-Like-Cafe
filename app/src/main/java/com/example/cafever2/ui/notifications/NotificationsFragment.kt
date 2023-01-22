package com.example.cafever2.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cafever2.MainActivity
import com.example.cafever2.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.R
import org.w3c.dom.Text

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // firebase auth
    var mAuth: FirebaseAuth? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private val TAG:String = "Result Activity"

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
//        val root: View = binding.root
        val root = inflater.inflate(com.example.cafever2.R.layout.fragment_notifications,container,false)

        //ดึงค่าจาก Firebase มาใส่ใน mAuth
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth!!.currentUser
        //นําค่ามาใส่ลงใน TextView ที่สร้างขึ้น
        val result_emailData = root.findViewById<TextView>(com.example.cafever2.R.id.result_emailData)
        val result_uidData = root.findViewById<TextView>(com.example.cafever2.R.id.result_uidData)
        result_emailData.text = user!!.email
        result_uidData.text = user.uid

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val users = firebaseAuth.currentUser
            if (users == null) {
                startActivity(Intent(context, MainActivity::class.java))
//                finish()
            }
        }
        // การทํางานของปุ่ม Sign out
        val result_signOutBtn = root.findViewById<Button>(com.example.cafever2.R.id.result_signOutBtn)
        result_signOutBtn.setOnClickListener {
            mAuth!!.signOut()
            Toast.makeText(context,"Signed out!", Toast.LENGTH_LONG).show()
            Log.d(TAG, "Signed out!")
            startActivity(Intent(context, MainActivity::class.java))
//            finish()
        }

        return root
    }
    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener { mAuthListener }
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) { mAuth!!.removeAuthStateListener { mAuthListener } }
    }
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK) { moveTaskToBack(true) }
//        return super.onKeyDown(keyCode, event)
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}