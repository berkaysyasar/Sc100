package com.berkay.loginscreens.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.berkay.loginscreens.R
import com.berkay.loginscreens.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val selectedCategories = intent.getStringArrayListExtra("selectedCategories")
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)



        selectedCategories!!.forEach {
            category ->
            val textView = TextView(this)
            textView.text = category
            linearLayout.addView(textView)

        }
    }
}