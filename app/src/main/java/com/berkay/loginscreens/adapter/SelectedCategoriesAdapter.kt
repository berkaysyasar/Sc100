package com.berkay.loginscreens.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.berkay.loginscreens.R

class SelectedCategoriesAdapter(private val selectedCategories: List<String>?) :
    RecyclerView.Adapter<SelectedCategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonItem: Button = itemView.findViewById(R.id.buttonItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = selectedCategories?.get(position)
        holder.buttonItem.text = category

        // Seçilen öğelerin üzerine tıklandığında bir şey yapmak istiyorsanız buraya ekleyebilirsiniz
        holder.itemView.setOnClickListener {
            // Örneğin: Toast.makeText(holder.itemView.context, "Tıklandı: $category", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return selectedCategories?.size ?: 0
    }
}