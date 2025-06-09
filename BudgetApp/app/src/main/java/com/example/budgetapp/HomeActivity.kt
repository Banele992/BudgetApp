package com.example.budgetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {
    private lateinit var minSpendingInput: TextInputEditText
    private lateinit var maxSpendingInput: TextInputEditText
    private lateinit var saveGoalsButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize views
        minSpendingInput = findViewById(R.id.minSpendingInput)
        maxSpendingInput = findViewById(R.id.maxSpendingInput)
        saveGoalsButton = findViewById(R.id.saveGoalsButton)

        // Set up bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Already on home
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
                R.id.navigation_settings -> {
                    // TODO: Navigate to settings
                    true
                }
                else -> false
            }
        }

        // Set up save goals button
        saveGoalsButton.setOnClickListener {
            saveSpendingGoals()
        }

        // Load saved goals if any
        loadSavedGoals()
    }

    private fun saveSpendingGoals() {
        val minSpending = minSpendingInput.text.toString()
        val maxSpending = maxSpendingInput.text.toString()

        if (minSpending.isEmpty() || maxSpending.isEmpty()) {
            Toast.makeText(this, "Please enter both minimum and maximum spending goals", Toast.LENGTH_SHORT).show()
            return
        }

        val minAmount = minSpending.toDoubleOrNull()
        val maxAmount = maxSpending.toDoubleOrNull()

        if (minAmount == null || maxAmount == null) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        if (minAmount >= maxAmount) {
            Toast.makeText(this, "Minimum spending must be less than maximum spending", Toast.LENGTH_SHORT).show()
            return
        }

        // Save to SharedPreferences
        getSharedPreferences("BudgetPrefs", MODE_PRIVATE).edit().apply {
            putFloat("min_spending", minAmount.toFloat())
            putFloat("max_spending", maxAmount.toFloat())
            apply()
        }

        Toast.makeText(this, "Spending goals saved successfully", Toast.LENGTH_SHORT).show()
    }

    private fun loadSavedGoals() {
        val prefs = getSharedPreferences("BudgetPrefs", MODE_PRIVATE)
        val minSpending = prefs.getFloat("min_spending", 0f)
        val maxSpending = prefs.getFloat("max_spending", 0f)

        if (minSpending > 0f) {
            minSpendingInput.setText(minSpending.toString())
        }
        if (maxSpending > 0f) {
            maxSpendingInput.setText(maxSpending.toString())
        }
    }
} 