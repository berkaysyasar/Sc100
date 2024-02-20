package com.berkay.loginscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.berkay.loginscreens.databinding.ActivityMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class menu : AppCompatActivity() {
    private lateinit var binding : ActivityMenuBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")
        binding.menutextview.setText("Welcome ${email}")
    }

    fun signoutButtonClicked(view: View){
        auth.signOut()
        startActivity(Intent(this,MainActivity::class.java))

    }
}