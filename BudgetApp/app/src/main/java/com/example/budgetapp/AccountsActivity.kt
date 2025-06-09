package com.example.budgetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AccountsActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val accounts = mutableListOf<BankAccount>()
    private lateinit var adapter: AccountsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts)

        val addAccountButton = findViewById<com.google.android.material.button.MaterialButton>(R.id.addAccountButton)
        addAccountButton.setOnClickListener {
            val intent = Intent(this, AddAccountActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.accountsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AccountsAdapter(accounts)
        recyclerView.adapter = adapter

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

    override fun onResume() {
        super.onResume()
        fetchAccounts()
    }

    private fun fetchAccounts() {
        db.collection("accounts").get()
            .addOnSuccessListener { result ->
                val newAccounts = mutableListOf<BankAccount>()
                for (doc in result) {
                    val account = BankAccount(
                        bankName = doc.getString("bankName") ?: "",
                        balance = doc.getDouble("balance") ?: 0.0,
                        cardNumber = doc.getString("cardNumber") ?: "",
                        cardType = doc.getString("cardType") ?: ""
                    )
                    newAccounts.add(account)
                }
                accounts.clear()
                accounts.addAll(newAccounts)
                adapter.notifyDataSetChanged()
            }
    }
}

data class BankAccount(
    val bankName: String,
    val balance: Double,
    val cardNumber: String,
    val cardType: String
) 