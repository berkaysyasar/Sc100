package com.berkay.loginscreens.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import com.berkay.loginscreens.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private val selectedCategories = mutableListOf<String>()

    // Switch'lerin durumlarını takip etmek için flag'ler
    private var switch1Checked = false
    private var switch2Checked = false
    private var switch3Checked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val switch1 = binding.notswitch
        val switch2 = binding.sporswitch
        val switch3 = binding.yemekswitch

        switch1.setOnCheckedChangeListener { _, isChecked ->
            switch1Checked = isChecked

        }

        switch2.setOnCheckedChangeListener { _, isChecked ->
            switch2Checked = isChecked

        }

        switch3.setOnCheckedChangeListener { _, isChecked ->
            switch3Checked = isChecked

        }

        binding.devametbutton.setOnClickListener {

                startNextActivityWithSelectedCategories()
            
        }
    }



    private fun startNextActivityWithSelectedCategories() {
        val selectedCategories = mutableListOf<String>()

        if (binding.notswitch.isChecked) {
            selectedCategories.add("Not")
        }

        if (binding.sporswitch.isChecked) {
            selectedCategories.add("Spor")
        }

        if (binding.yemekswitch.isChecked) {
            selectedCategories.add("Yemek")
        }

        val intent = Intent(this, MainMenuActivity::class.java)
        intent.putExtra("selectedCategories", ArrayList(selectedCategories))
        startActivity(intent)
    }
}





