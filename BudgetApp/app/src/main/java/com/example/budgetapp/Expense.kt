package com.example.budgetapp

data class Expense(
    val id: String = "",
    val name: String = "",
    val amount: Double = 0.0,
    val notes: String = "",
    val category: String = "",
    val receiptImageUrl: String = ""
) 