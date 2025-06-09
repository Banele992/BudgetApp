package com.example.budgetapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val optionLabels = listOf(
            "Notifications", "Privacy", "Security", "Chat", "Account", "Help", "About", "Report", "Logout"
        )

        val optionIds = listOf(
            R.id.optionLabel
        )

        // Set labels for each option
        for (i in optionLabels.indices) {
            findViewById<TextView>(optionIds[i])?.text = optionLabels[i]
        }
    }
}