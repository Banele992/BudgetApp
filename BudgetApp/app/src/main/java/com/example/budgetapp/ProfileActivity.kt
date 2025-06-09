package com.example.budgetapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Dummy data for now
        val profileImage = findViewById<ImageView>(R.id.profileImage)
        val profileName = findViewById<TextView>(R.id.profileName)
        val profileEmail = findViewById<TextView>(R.id.profileEmail)
        val editProfileButton = findViewById<MaterialButton>(R.id.editProfileButton)

        profileImage.setImageResource(R.drawable.ic_profile)
        profileName.text = "Sabrina Aryan"
        profileEmail.text = "SabrinaAry208@gmailcom"
        editProfileButton.setOnClickListener {
            // TODO: Implement edit profile functionality
        }

        // Set up back icon click listener
        val backIcon = findViewById<ImageView>(R.id.backIcon)
        backIcon.setOnClickListener {
            finish()
        }

        // Set up settings icon click listener
        val settingsIcon = findViewById<ImageView>(R.id.settingsIcon)
        settingsIcon.setOnClickListener {
            // TODO: Navigate to settings or show settings dialog
        }

        // Set up RecyclerView for profile options
        val options = listOf(
            SettingsAdapter.SettingsOption("Favourites", R.drawable.ic_profile),
            SettingsAdapter.SettingsOption("Downloads", R.drawable.ic_profile),
            SettingsAdapter.SettingsOption("Languages", R.drawable.ic_profile),
            SettingsAdapter.SettingsOption("Location", R.drawable.ic_profile),
            SettingsAdapter.SettingsOption("Subscription", R.drawable.ic_profile),
            SettingsAdapter.SettingsOption("Display", R.drawable.ic_profile),
            SettingsAdapter.SettingsOption("Clear Cache", R.drawable.ic_profile),
            SettingsAdapter.SettingsOption("Clear History", R.drawable.ic_profile)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.profileOptionsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = SettingsAdapter(options) { option ->
            when (option.label) {
                "Favourites" -> {
                    // TODO: Navigate to favourites
                }
                "Downloads" -> {
                    // TODO: Navigate to downloads
                }
                "Languages" -> {
                    // TODO: Show language selection dialog
                }
                "Location" -> {
                    // TODO: Show location settings
                }
                "Subscription" -> {
                    // TODO: Navigate to subscription page
                }
                "Display" -> {
                    // TODO: Show display settings
                }
                "Clear Cache" -> {
                    // TODO: Clear cache
                }
                "Clear History" -> {
                    // TODO: Clear history
                }
            }
        }
    }
} 