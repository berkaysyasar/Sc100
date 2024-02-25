package com.berkay.loginscreens.view

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

        binding.appinfoTextView.setText("Welcome to SelfControl, where every tap is a step towards your goals. In this digital realm, you are the architect of your focus. Embrace the challenge, for within discipline lies your path to success. Let each moment of restraint be a reminder of your unwavering commitment to personal growth. Welcome to a life shaped by your choices – welcome to SelfControl!")
        binding.continueButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}