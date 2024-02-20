package com.berkay.loginscreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.berkay.loginscreens.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun submitClicked(view: View){
        val email = binding.emailText.text.toString().trim{
            it <= ' '
        }
        if(email.isEmpty()){
            Toast.makeText(this,"Please enter email address!",Toast.LENGTH_LONG).show()
        }else{
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Email sent successfully to reset your password!",Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }

    }


}