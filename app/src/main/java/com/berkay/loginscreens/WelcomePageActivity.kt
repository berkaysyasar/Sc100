package com.berkay.loginscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.berkay.loginscreens.databinding.ActivityWelcomePageBinding

class WelcomePageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.continueButton.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}