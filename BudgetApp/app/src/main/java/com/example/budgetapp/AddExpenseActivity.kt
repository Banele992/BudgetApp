package com.example.budgetapp

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {
    private lateinit var dateText: TextView
    private lateinit var amountInput: EditText
    private lateinit var notesInput: EditText
    private lateinit var addButton: Button
    private lateinit var addReceiptBtn: Button
    private lateinit var categorySpinner: Spinner
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private var selectedCategory: String = ""
    private var selectedDate: String = ""
    private var receiptImageUrl: String = ""
    private var receiptImageUri: Uri? = null
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        dateText = findViewById(R.id.expenseDateText)
        amountInput = findViewById(R.id.expenseAmountInput)
        notesInput = findViewById(R.id.expenseNotesInput)
        addButton = findViewById(R.id.addExpenseBtn)
        addReceiptBtn = findViewById(R.id.addReceiptBtn)
        categorySpinner = findViewById(R.id.categorySpinner)

        // Set today's date as default
        val calendar = Calendar.getInstance()
        selectedDate = dateFormat.format(calendar.time)
        dateText.text = selectedDate

        dateText.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, { _, y, m, d ->
                val cal = Calendar.getInstance()
                cal.set(y, m, d)
                selectedDate = dateFormat.format(cal.time)
                dateText.text = selectedDate
            }, year, month, day)
            dpd.show()
        }

        // Fetch categories for spinner
        fetchCategoriesForSpinner()

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                selectedCategory = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        addReceiptBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Receipt Image"), PICK_IMAGE_REQUEST)
        }

        addButton.setOnClickListener {
            val amount = amountInput.text.toString().trim()
            val notes = notesInput.text.toString().trim()

            if (amount.isEmpty() || selectedCategory.isEmpty()) {
                Toast.makeText(this, "Amount and category are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (receiptImageUri != null) {
                uploadReceiptAndSaveExpense(amount, notes)
            } else {
                saveExpense(amount, notes, "")
            }
        }
    }

    private fun fetchCategoriesForSpinner() {
        db.collection("categories").get().addOnSuccessListener { result ->
            val categoryNames = result.mapNotNull { it.getString("name") }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
            if (categoryNames.isNotEmpty()) {
                selectedCategory = categoryNames[0]
            }
        }
    }

    private fun uploadReceiptAndSaveExpense(amount: String, notes: String) {
        val ref = storage.reference.child("receipts/${UUID.randomUUID()}.jpg")
        receiptImageUri?.let { uri ->
            ref.putFile(uri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { downloadUri ->
                        saveExpense(amount, notes, downloadUri.toString())
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to upload receipt", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveExpense(amount: String, notes: String, receiptUrl: String) {
        val expense = hashMapOf(
            "amount" to amount.toDoubleOrNull(),
            "notes" to notes,
            "category" to selectedCategory,
            "date" to selectedDate,
            "receiptImageUrl" to receiptUrl
        )
        db.collection("expenses")
            .add(expense)
            .addOnSuccessListener {
                Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add expense", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            receiptImageUri = data.data
            addReceiptBtn.text = "Receipt Added"
        }
    }
} 