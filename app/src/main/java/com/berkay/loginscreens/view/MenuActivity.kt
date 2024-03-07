package com.berkay.loginscreens.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkay.loginscreens.adapter.CreateCategoryAdapter
import com.berkay.loginscreens.database.dbhelper
import com.berkay.loginscreens.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private val selectedCategories = mutableListOf<CategorySwitchItem>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var createCategorieAdapter: CreateCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val context = this
        var db = dbhelper(context)

        // Sadece "Not" switch'ini kullanacağız
        val switch1 = binding.noteswitch
        val addButton = binding.addButton

        addButton.setOnClickListener {
            showAddCategoryDialog()
        }

        // "Not" switch'ini her zaman enable ve değiştirilemez yap
        switch1.isChecked = true
        switch1.isEnabled = false
        switch1.isClickable = false

        binding.devametbutton.setOnClickListener {
            startNextActivityWithSelectedCategories()
        }

        recyclerView = binding.recyclerView2
        recyclerView.layoutManager = LinearLayoutManager(this)
        createCategorieAdapter = CreateCategoryAdapter(selectedCategories)
        recyclerView.adapter = createCategorieAdapter
    }

    private fun showAddCategoryDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Kategori Ekle")

        val input = EditText(this)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        builder.setView(input)

        builder.setPositiveButton("Ekle") { _, _ ->
            val categoryName = input.text.toString()
            if (categoryName.length in 2..10) {
                val formattedCategoryName = categoryName.capitalize()

                if (!selectedCategories.any { it.category == formattedCategoryName }) {
                    // Yeni kategori eklenirse, switch durumunu varsayılan olarak false yap
                    selectedCategories.add(CategorySwitchItem(formattedCategoryName, false))
                    createCategorieAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Bu kategori zaten ekli.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Kategori adı 2 ile 10 karakter arasında olmalıdır.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton("İptal") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun startNextActivityWithSelectedCategories() {
        val selectedCategoriesWithSwitch = selectedCategories.filter { it.isChecked }.map { it.category }
        val intent = Intent(this, MainMenuActivity::class.java)
        intent.putStringArrayListExtra("selectedCategoriesWithSwitch", ArrayList(selectedCategoriesWithSwitch))
        startActivity(intent)
    }

    data class CategorySwitchItem(val category: String, var isChecked: Boolean)
}
