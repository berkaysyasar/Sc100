package com.berkay.loginscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.berkay.loginscreens.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        

    }

    fun signupClicked(view: View){
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        val passwordcontrol = binding.passwordcontrolText.text.toString()
        if(email.equals("") || password.equals("") || passwordcontrol.equals("")){
            Toast.makeText(this,"Enter email and password!", Toast.LENGTH_LONG).show()
        }else if(password != passwordcontrol){
            Toast.makeText(this,"Password not matching!", Toast.LENGTH_LONG).show()
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent = Intent(this@SignupActivity,menu::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this@SignupActivity,it.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }

    }
}