package com.berkay.loginscreens.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.berkay.loginscreens.R
import com.berkay.loginscreens.view.MenuActivity.CategorySwitchItem

class CreateCategoryAdapter(private val categories: MutableList<CategorySwitchItem>) :
    RecyclerView.Adapter<CreateCategoryAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.category)
        val switch: Switch = itemView.findViewById(R.id.categoryswitch)

        init {
            // Switch durumunu değiştirdiğimizde, CategorySwitchItem'ı güncelleyerek kaydediyoruz
            switch.setOnCheckedChangeListener { _, isChecked ->
                categories[adapterPosition].isChecked = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryItem = categories[position]
        holder.categoryName.text = categoryItem.category
        holder.switch.isChecked = categoryItem.isChecked
        // Burada gerekirse switch'in text veya başka özelliklerini de belirleyebilirsiniz
        holder.switch.text = ""
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}
