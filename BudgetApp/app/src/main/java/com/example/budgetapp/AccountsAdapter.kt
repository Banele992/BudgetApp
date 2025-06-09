package com.example.budgetapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AccountsAdapter(private val accounts: List<BankAccount>) : RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account_card, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accounts[position]
        holder.balance.text = "R${"%,.2f".format(account.balance)}"
        holder.cardNumber.text = account.cardNumber
        holder.bankName.text = account.bankName
        holder.cardTypeIcon.setImageResource(
            when (account.cardType) {
                "Mastercard" -> R.drawable.ic_mastercard
                "Visa" -> R.drawable.ic_visa
                else -> R.drawable.ic_credit_card
            }
        )
    }

    override fun getItemCount(): Int = accounts.size

    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val balance: TextView = itemView.findViewById(R.id.accountBalance)
        val cardNumber: TextView = itemView.findViewById(R.id.accountCardNumber)
        val bankName: TextView = itemView.findViewById(R.id.accountBankName)
        val cardTypeIcon: ImageView = itemView.findViewById(R.id.accountCardTypeIcon)
    }
} 