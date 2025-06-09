package com.example.budgetapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore



class AddCategoryActivity : AppCompatActivity() {
    private lateinit var nameInput: EditText
    private lateinit var budgetInput: EditText
    private lateinit var notesInput: EditText
    private lateinit var addButton: Button
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        nameInput = findViewById(R.id.categoryNameInput)
        budgetInput = findViewById(R.id.categoryBudgetInput)
        notesInput = findViewById(R.id.categoryNotesInput)
        addButton = findViewById(R.id.addCategoryBtn)

        addButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val budget = budgetInput.text.toString().trim()
            val notes = notesInput.text.toString().trim()

            if (name.isEmpty() || budget.isEmpty()) {
                Toast.makeText(this, "Name and budget are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val category = hashMapOf(
                "name" to name,
                "budget" to budget.toDoubleOrNull(),
                "notes" to notes
            )

            db.collection("categories")
                .add(category)
                .addOnSuccessListener {
                    Toast.makeText(this, "Category added", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add category", Toast.LENGTH_SHORT).show()
                }
        }
    }
} 