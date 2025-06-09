package com.example.budgetapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import android.graphics.Color
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class GrowthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_growth)

        val pieChart = findViewById<PieChart>(R.id.growthPieChart)
        val categories = listOf(
            Category(name = "Categories", budget = 600.0),
            Category(name = "Transportation", budget = 600.0),
            Category(name = "Charity", budget = 1250.0),
            Category(name = "Food", budget = 900.0),
            Category(name = "Shopping", budget = 400.0),
            Category(name = "Health", budget = 300.0),
            Category(name = "Education", budget = 700.0),
            Category(name = "Entertainment", budget = 500.0)
        )
        val entries = categories.map { PieEntry(it.budget.toFloat(), it.name) }
        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(
                Color.parseColor("#A259FF"),
                Color.parseColor("#4B93FF"),
                Color.parseColor("#FF6B6B"),
                Color.parseColor("#FFC542"),
                Color.parseColor("#43E97B"),
                Color.parseColor("#FF8C42"),
                Color.parseColor("#36CFC9"),
                Color.parseColor("#FF5CA7")
            )
            valueTextColor = Color.WHITE
            valueTextSize = 16f
        }
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.setDrawEntryLabels(false)
        pieChart.description.isEnabled = false
        val topCategories = categories.take(1).joinToString(", ") { it.name }
        pieChart.centerText = " $topCategories"
        pieChart.setCenterTextSize(16f)
        pieChart.setCenterTextColor(Color.WHITE)
        pieChart.setHoleColor(Color.parseColor("#22223B"))
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
        pieChart.legend.textColor = Color.WHITE
        pieChart.legend.textSize = 14f
        pieChart.invalidate()

        // Line chart for timeline and goals
        val lineChart = findViewById<com.github.mikephil.charting.charts.LineChart>(R.id.growthLineChart)
        val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
        val actuals = listOf(500f, 800f, 1200f, 900f, 1500f, 1100f)
        val goals = listOf(700f, 900f, 1100f, 1300f, 1400f, 1200f)
        val actualEntries = actuals.mapIndexed { i, v -> com.github.mikephil.charting.data.Entry(i.toFloat(), v) }
        val goalEntries = goals.mapIndexed { i, v -> com.github.mikephil.charting.data.Entry(i.toFloat(), v) }
        val actualSet = com.github.mikephil.charting.data.LineDataSet(actualEntries, "Actual").apply {
            color = Color.parseColor("#4B93FF")
            valueTextColor = Color.WHITE
            lineWidth = 3f
            setDrawCircles(true)
            setCircleColor(Color.parseColor("#4B93FF"))
            circleRadius = 5f
            setDrawValues(false)
        }
        val goalSet = com.github.mikephil.charting.data.LineDataSet(goalEntries, "Goal").apply {
            color = Color.parseColor("#FFC542")
            valueTextColor = Color.WHITE
            lineWidth = 3f
            setDrawCircles(true)
            setCircleColor(Color.parseColor("#FFC542"))
            circleRadius = 5f
            setDrawValues(false)
            enableDashedLine(10f, 10f, 0f)
        }
        val lineData = com.github.mikephil.charting.data.LineData(actualSet, goalSet)
        lineChart.data = lineData
        lineChart.setBackgroundColor(Color.parseColor("#22223B"))
        lineChart.description.isEnabled = false
        lineChart.axisLeft.textColor = Color.WHITE
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.textColor = Color.WHITE
        lineChart.xAxis.valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(months)
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        lineChart.legend.textColor = Color.WHITE
        lineChart.invalidate()

        // Progress bar logic
        val progressBar = findViewById<android.widget.ProgressBar>(R.id.progressBar)
        val progressSummary = findViewById<android.widget.TextView>(R.id.progressSummary)
        val totalActual = 1100f // Example: last value in actuals
        val totalGoal = 1200f // Example: last value in goals
        val percent = ((totalActual / totalGoal) * 100).toInt().coerceAtMost(100)
        progressBar.progress = percent
        progressSummary.text = "${percent}% of your goal reached this month"

        val bottomNav = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.selectedItemId = R.id.navigation_growth
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
                R.id.navigation_growth -> true // Already here
                R.id.navigation_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
} 