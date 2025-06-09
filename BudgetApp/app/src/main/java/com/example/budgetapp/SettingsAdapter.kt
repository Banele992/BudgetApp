package com.example.budgetapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SettingsAdapter(
    private val options: List<SettingsOption>
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    data class SettingsOption(val label: String, val iconRes: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_settings_option, parent, false)
        return SettingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val option = options[position]
        holder.label.text = option.label
        holder.icon.setImageResource(option.iconRes)
    }

    override fun getItemCount(): Int = options.size

    class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView = itemView.findViewById(R.id.optionLabel)
        val icon: ImageView = itemView.findViewById(R.id.optionIcon)
    }
} 