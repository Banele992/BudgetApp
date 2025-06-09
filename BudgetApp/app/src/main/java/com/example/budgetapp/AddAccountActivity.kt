package com.example.budgetapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class AddAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)

        val bankNameInput = findViewById<TextInputEditText>(R.id.bankNameInput)
        val balanceInput = findViewById<TextInputEditText>(R.id.balanceInput)
        val cardNumberInput = findViewById<TextInputEditText>(R.id.cardNumberInput)
        val cardTypeInput = findViewById<TextInputEditText>(R.id.cardTypeInput)
        val saveAccountButton = findViewById<MaterialButton>(R.id.saveAccountButton)

        saveAccountButton.setOnClickListener {
            val bankName = bankNameInput.text.toString().trim()
            val balance = balanceInput.text.toString().trim().toDoubleOrNull()
            val cardNumber = cardNumberInput.text.toString().trim()
            val cardType = cardTypeInput.text.toString().trim()

            if (bankName.isEmpty() || balance == null || cardNumber.isEmpty() || cardType.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newAccount = BankAccount(bankName, balance, cardNumber, cardType)
            // Save the new account to Firestore
            val db = FirebaseFirestore.getInstance()
            val accountData = hashMapOf(
                "bankName" to bankName,
                "balance" to balance,
                "cardNumber" to cardNumber,
                "cardType" to cardType
            )
            db.collection("accounts")
                .add(accountData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Account saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save account", Toast.LENGTH_SHORT).show()
                }
        }
    }
} 