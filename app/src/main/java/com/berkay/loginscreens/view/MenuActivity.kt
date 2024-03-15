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
import com.berkay.loginscreens.classes.CategoryMaker
import com.berkay.loginscreens.database.dbhelper
import com.berkay.loginscreens.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private val selectedCategories = mutableListOf<CategorySwitchItem>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var createCategorieAdapter: CreateCategoryAdapter
    private lateinit var db: dbhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = dbhelper(this)


        // Sadece "Not" switch'ini kullanacağız
        val switch1 = binding.noteswitch
        val addButton = binding.addButton
        val deleteButton = binding.deletebutton

        addButton.setOnClickListener {
            showAddCategoryDialog()
        }

        deleteButton.setOnClickListener {
            showDeleteCategoryDialog()
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

    private fun showDeleteCategoryDialog() {
        val categoryList = selectedCategories.map { it.category }.toTypedArray()

        val builder = AlertDialog.Builder(this@MenuActivity)
        builder.setTitle("Delete Category")

        builder.setItems(categoryList) { _, which ->
            val selectedCategory = categoryList[which]
            showDeleteConfirmationDialog(selectedCategory)
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun showDeleteConfirmationDialog(categoryName: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deletion Confirmation")
        builder.setMessage("Are you sure you want to delete the $categoryName category?")

        builder.setPositiveButton("Delete") { _, _ ->
            val isDeleted = db.deleteData(categoryName)
            if (isDeleted) {
                Toast.makeText(this, "$categoryName deleted.", Toast.LENGTH_SHORT).show()
                // Kategori listesini güncelle
                refreshCategoryList()
            } else {
                Toast.makeText(this, "An error occurred while deleting a category.", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }



    override fun onPause() {
        super.onPause()
        // onPause'de sadece seçilen kategorilerin durumunu bir yerde sakla
        saveSelectedCategoriesState()
    }

    override fun onResume() {
        super.onResume()
        if (::db.isInitialized) {
            // onResume'de kategorilerin listesini güncelle
            refreshCategoryList()

            // onResume'de sadece seçilen kategorilerin durumunu geri yükle
            restoreSelectedCategoriesState()
        }
    }

    private fun saveSelectedCategoriesState() {
        // SharedPreferences veya başka bir veritabanı yöntemiyle seçilen kategorilerin durumunu kaydet
        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = prefs.edit()

        selectedCategories.forEach {
            editor.putBoolean(it.category, it.isChecked)
        }

        editor.apply()
    }
    private fun restoreSelectedCategoriesState() {
        // SharedPreferences veya başka bir veritabanı yöntemiyle seçilen kategorilerin durumunu geri yükle
        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        selectedCategories.forEach {
            it.isChecked = prefs.getBoolean(it.category, false)
        }

        // Adapter'a veri değişikliğini bildir
        createCategorieAdapter.notifyDataSetChanged()
    }

    private fun refreshCategoryList() {
        // Seçili kategorilerin durumunu önce saklayın
        val previousSelectedCategories = selectedCategories.filter { it.isChecked }

        // readData fonksiyonunu kullanarak verileri çek
        val data = db.readData()
        selectedCategories.clear() // Önceki verileri temizle
        selectedCategories.addAll(data.map { categoryMaker ->
            // Mevcut veritabanı kategorileriyle eşleşen önceki seçili kategorilerin durumunu bulun
            val isChecked = previousSelectedCategories.any { it.category == categoryMaker.categoryname && it.isChecked }
            CategorySwitchItem(categoryMaker.categoryname, isChecked)
        })

        // Adapter'a veri değişikliğini bildir
        createCategorieAdapter.notifyDataSetChanged()

        // RecyclerView'ı güncelle
        recyclerView.adapter = createCategorieAdapter
    }



    private fun showAddCategoryDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Category")

        val input = EditText(this)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        builder.setView(input)

        builder.setPositiveButton("Add") { _, _ ->
            val categoryName = input.text.toString()
            if (categoryName.length in 2..10) {
                val formattedCategoryName = capitalizeFirstLetter(categoryName)

                if (!selectedCategories.any { it.category.equals(formattedCategoryName, ignoreCase = true) }) {
                    // Yeni kategori eklenirse, switch durumunu varsayılan olarak false yap
                    val newCategory = CategorySwitchItem(formattedCategoryName, false)
                    selectedCategories.add(newCategory)
                    createCategorieAdapter.notifyDataSetChanged()

                    // Yeni kategoriyi veritabanına ekle
                    val categoryMaker = CategoryMaker(formattedCategoryName, false)
                    db.insertData(categoryMaker)

                    // Kategori listesini güncelle
                    refreshCategoryList()
                } else {
                    Toast.makeText(this, "This category is already attached.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "The category name must be between 2 and 10 characters.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun capitalizeFirstLetter(input: String): String {
        return input.substring(0, 1).toUpperCase() + input.substring(1)
    }



    private fun startNextActivityWithSelectedCategories() {
        val selectedCategoriesWithSwitch = selectedCategories.filter { it.isChecked }.map { it.category }
        val intent = Intent(this, MainMenuActivity::class.java)
        intent.putStringArrayListExtra("selectedCategoriesWithSwitch", ArrayList(selectedCategoriesWithSwitch))
        startActivity(intent)
    }

    data class CategorySwitchItem(val category: String, var isChecked: Boolean)
}

