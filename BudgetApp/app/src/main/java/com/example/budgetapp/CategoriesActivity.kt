package com.example.budgetapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class CategoriesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addCategoryButton: com.google.android.material.button.MaterialButton
    private lateinit var adapter: CategoryAdapter
    private val db = FirebaseFirestore.getInstance()
    private val categories = mutableListOf<Category>()
    private lateinit var totalText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        recyclerView = findViewById(R.id.categoriesRecyclerView)
        addCategoryButton = findViewById(R.id.addCategoryButton)
        totalText = findViewById(R.id.categoriesTotalText)

        adapter = CategoryAdapter(categories)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addCategoryButton.setOnClickListener {
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivity(intent)
        }

        val bottomNav = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.selectedItemId = R.id.navigation_categories
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_categories -> true // Already here
                R.id.navigation_expenses -> true // TODO: Implement navigation
                R.id.navigation_growth -> true // TODO: Implement navigation
                R.id.navigation_settings -> true // TODO: Implement navigation
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchCategories()
    }

    private fun fetchCategories() {
        db.collection("categories").get()
            .addOnSuccessListener { result ->
                val newCategories = mutableListOf<Category>()
                for (doc in result) {
                    val category = Category(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        budget = doc.getDouble("budget") ?: 0.0,
                        notes = doc.getString("notes") ?: ""
                    )
                    newCategories.add(category)
                }
                categories.clear()
                categories.addAll(newCategories)
                adapter.updateCategories(categories)
                updateTotal()
            }
    }

    private fun updateTotal() {
        val total = categories.sumOf { it.budget }
        totalText.text = "Total: R${"%.2f".format(total)}"
    }
} 