package com.berkay.loginscreens.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkay.loginscreens.adapter.SelectedCategoriesAdapter
import com.berkay.loginscreens.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var selectedCategoriesAdapter: SelectedCategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val selectedCategories = intent.getStringArrayListExtra("selectedCategories")
        selectedCategoriesAdapter = SelectedCategoriesAdapter(selectedCategories)
        recyclerView.adapter = selectedCategoriesAdapter
    }

    // Diğer kodlar...
}
