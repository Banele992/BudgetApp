package com.example.budgetapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class ExpensesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addExpenseButton: com.google.android.material.button.MaterialButton
    private lateinit var adapter: ExpenseAdapter
    private val db = FirebaseFirestore.getInstance()
    private val expenses = mutableListOf<Expense>()
    private lateinit var totalText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        recyclerView = findViewById(R.id.expensesRecyclerView)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        totalText = findViewById(R.id.expensesTotalText)

        adapter = ExpenseAdapter(expenses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addExpenseButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        val bottomNav = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.selectedItemId = R.id.navigation_expenses
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
                R.id.navigation_expenses -> true // Already here
                R.id.navigation_growth -> {
                    val intent = Intent(this, GrowthActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchExpenses()
    }

    private fun fetchExpenses() {
        db.collection("expenses").get()
            .addOnSuccessListener { result ->
                val newExpenses = mutableListOf<Expense>()
                for (doc in result) {
                    val expense = Expense(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        amount = doc.getDouble("amount") ?: 0.0,
                        notes = doc.getString("notes") ?: ""
                    )
                    newExpenses.add(expense)
                }
                expenses.clear()
                expenses.addAll(newExpenses)
                adapter.updateExpenses(expenses)
                updateTotal()
            }
    }

    private fun updateTotal() {
        val total = expenses.sumOf { it.amount }
        totalText.text = "Total: R${"%.2f".format(total)}"
    }
} 