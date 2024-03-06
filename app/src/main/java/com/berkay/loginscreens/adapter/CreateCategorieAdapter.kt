package com.berkay.loginscreens.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.berkay.loginscreens.R

class CreateCategorieAdapter(private val categories: MutableList<String>) :
    RecyclerView.Adapter<CreateCategorieAdapter.ViewHolder>() {

    private val isCheckedList = MutableList(categories.size) { false }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.category)
        val switch: Switch = itemView.findViewById(R.id.switch1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category
        // Burada switch'in durumunu belirleyebilirsiniz
        holder.switch.isChecked = false
        holder.switch.text = ""
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun getCheckedStatus(position: Int): Boolean {
        return isCheckedList.getOrNull(position) ?: false
    }
}