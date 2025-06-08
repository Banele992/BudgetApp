package com.example.budgetapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true // Already on home
                R.id.nav_categories -> true // TODO: Navigate to Categories
                R.id.nav_expenses -> true // TODO: Navigate to Expenses
                R.id.nav_growth -> true // TODO: Navigate to Growth
                R.id.nav_settings -> true // TODO: Navigate to Settings
                else -> false
            }
        }

        var minGoal = 1000f
        var maxGoal = 6000f
        val spent = 2335.2f
        val left = maxGoal - spent

        val minGoalText = findViewById<TextView>(R.id.minGoalText)
        val maxGoalText = findViewById<TextView>(R.id.maxGoalText)
        val spentText = findViewById<TextView>(R.id.spentText)
        val leftToSpendText = findViewById<TextView>(R.id.leftToSpendText)
        val monthlyBudgetText = findViewById<TextView>(R.id.monthlyBudgetText)
        val adjustBudget = findViewById<TextView>(R.id.adjustBudget)
        val pieChart = findViewById<PieChart>(R.id.budgetPieChart)

        fun updateGoals() {
            minGoalText.text = "Min: $" + String.format("%.0f", minGoal)
            maxGoalText.text = "Max: $" + String.format("%.0f", maxGoal)
            spentText.text = "$" + String.format("%.2f", spent) + " Spent"
            leftToSpendText.text = "Left to spend: $" + String.format("%.2f", left)
            monthlyBudgetText.text = "$" + String.format("%.0f", maxGoal)
        }
        updateGoals()

        adjustBudget.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_2, null)
            val minInput = EditText(this)
            minInput.hint = "Min Goal"
            minInput.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            minInput.setText(minGoal.toInt().toString())
            val maxInput = EditText(this)
            maxInput.hint = "Max Goal"
            maxInput.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            maxInput.setText(maxGoal.toInt().toString())
            val layout = android.widget.LinearLayout(this)
            layout.orientation = android.widget.LinearLayout.VERTICAL
            layout.addView(minInput)
            layout.addView(maxInput)
            AlertDialog.Builder(this)
                .setTitle("Adjust Budget Goals")
                .setView(layout)
                .setPositiveButton("Save") { _, _ ->
                    minGoal = minInput.text.toString().toFloatOrNull() ?: minGoal
                    maxGoal = maxInput.text.toString().toFloatOrNull() ?: maxGoal
                    updateGoals()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Pie chart setup
        val entries = listOf(
            PieEntry(1200f, "General"),
            PieEntry(600f, "Transport"),
            PieEntry(535.2f, "Charity")
        )
        val dataSet = PieDataSet(entries, "Budget")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 14f
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.centerText = "Spent\n$2,335.20"
        pieChart.setCenterTextSize(16f)
        pieChart.legend.isEnabled = false
        pieChart.invalidate()
    }
} 