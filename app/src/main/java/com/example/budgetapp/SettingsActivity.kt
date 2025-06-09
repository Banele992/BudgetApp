package com.example.budgetapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val optionLabels = listOf(
            "Notifications", "Privacy", "Security", "Chat", "Account", "Help", "About", "Report", "Logout"
        )
        val optionIcons = listOf(
            R.drawable.ic_notifications, // You can use your own vector icons or system icons
            R.drawable.ic_privacy,
            R.drawable.ic_security,
            R.drawable.ic_chat,
            R.drawable.ic_account,
            R.drawable.ic_help,
            R.drawable.ic_about,
            R.drawable.ic_report,
            R.drawable.ic_logout
        )

        val optionIds = listOf(
            R.id.optionLabel, R.id.optionLabel2, R.id.optionLabel3, R.id.optionLabel4, R.id.optionLabel5,
            R.id.optionLabel6, R.id.optionLabel7, R.id.optionLabel8, R.id.optionLabel9
        )
        val iconIds = listOf(
            R.id.optionIcon, R.id.optionIcon2, R.id.optionIcon3, R.id.optionIcon4, R.id.optionIcon5,
            R.id.optionIcon6, R.id.optionIcon7, R.id.optionIcon8, R.id.optionIcon9
        )

        // Set labels and icons for each option
        for (i in optionLabels.indices) {
            findViewById<TextView>(optionIds[i])?.text = optionLabels[i]
            findViewById<ImageView>(iconIds[i])?.setImageResource(optionIcons[i])
        }
    }
} 