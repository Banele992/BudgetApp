package com.example.budgetapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val options = listOf(
            SettingsAdapter.SettingsOption("Profile", R.drawable.ic_profile),
            SettingsAdapter.SettingsOption("Privacy", R.drawable.ic_privacy),
            SettingsAdapter.SettingsOption("Security", R.drawable.ic_security),
            SettingsAdapter.SettingsOption("Chat", R.drawable.ic_chat),
            SettingsAdapter.SettingsOption("Account", R.drawable.ic_account),
            SettingsAdapter.SettingsOption("Help", R.drawable.ic_help),
            SettingsAdapter.SettingsOption("About", R.drawable.ic_about),
            SettingsAdapter.SettingsOption("Report", R.drawable.ic_report),
            SettingsAdapter.SettingsOption("Logout", R.drawable.ic_logout)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.settingsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SettingsAdapter(options) { option ->
            if (option.label == "Profile") {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            } else if (option.label == "Account") {
                val intent = Intent(this, AccountsActivity::class.java)
                startActivity(intent)
            }
        }

        val bottomNav = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.selectedItemId = R.id.navigation_settings
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_categories -> {
                    val intent = Intent(this, CategoriesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_expenses -> {
                    val intent = Intent(this, ExpensesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_growth -> {
                    val intent = Intent(this, GrowthActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_settings -> true // Already here
                else -> false
            }
        }
    }
}