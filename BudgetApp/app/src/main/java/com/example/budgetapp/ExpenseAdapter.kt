package com.example.budgetapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ExpenseAdapter(private var expenses: List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.expenseName)
        val amountText: TextView = itemView.findViewById(R.id.expenseAmount)
        val notesText: TextView = itemView.findViewById(R.id.expenseNotes)
        val receiptImage: ImageView = itemView.findViewById(R.id.receiptImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.nameText.text = expense.name
        holder.amountText.text = "Amount: R${expense.amount}"
        holder.notesText.text = expense.notes
        if (expense.receiptImageUrl.isNotEmpty()) {
            Glide.with(holder.receiptImage.context)
                .load(expense.receiptImageUrl)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(holder.receiptImage)
        } else {
            holder.receiptImage.setImageResource(android.R.drawable.ic_menu_report_image)
        }
    }

    override fun getItemCount(): Int = expenses.size

    fun updateExpenses(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
} 