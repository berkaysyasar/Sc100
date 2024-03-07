package com.berkay.loginscreens.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkay.loginscreens.adapter.SelectedCategoriesAdapter
import com.berkay.loginscreens.interfaces.CategoryClickListener
import com.berkay.loginscreens.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity(), CategoryClickListener {

    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var selectedCategoriesAdapter: SelectedCategoriesAdapter
    private lateinit var selectedCategories: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val notButton = binding.notbutton
        notButton.text = "Not"
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Menü aktivitesinden gelen seçilen kategorileri al
        selectedCategories = intent.getStringArrayListExtra("selectedCategoriesWithSwitch") ?: mutableListOf()

        // Seçilen kategorileri RecyclerView ile göster
        selectedCategoriesAdapter = SelectedCategoriesAdapter(selectedCategories, this)
        recyclerView.adapter = selectedCategoriesAdapter

        // Adapter'a CategoryClickListener'ı set et
        selectedCategoriesAdapter.setOnCategoryClickListener(this)
    }

    override fun onCategoryClicked(category: String) {
        // Burada tıklanan kategoriye göre yapılacak işlemleri gerçekleştirebilirsiniz
        when (category) {
            "Not" -> {
                Toast.makeText(this, "Not kategorisine tıklandı", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
            // Diğer kategorilere tıklandığında yapılacak işlemler
        }
    }
}
