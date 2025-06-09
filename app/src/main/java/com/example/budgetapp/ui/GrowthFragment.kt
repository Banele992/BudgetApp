package com.example.budgetapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.budgetapp.R
import com.example.budgetapp.models.Expense
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class GrowthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_growth, container, false)
        val pieChart = view.findViewById<PieChart>(R.id.pieChart)

        // Dummy data for demonstration. Replace with real data from your database.
        val expenses = listOf(
            Expense(category = "Food", amount = 120.0),
            Expense(category = "Transport", amount = 80.0),
            Expense(category = "Shopping", amount = 200.0),
            Expense(category = "Bills", amount = 150.0)
        )

        val categoryTotals = expenses.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        val entries = categoryTotals.map { (category, total) ->
            PieEntry(total.toFloat(), category)
        }

        val dataSet = PieDataSet(entries, "Expenses by Category")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 16f

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.centerText = "Expenses"
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setCenterTextColor(Color.WHITE)
        pieChart.animateY(1000)
        pieChart.invalidate()

        return view
    }
} 