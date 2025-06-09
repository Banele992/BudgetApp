package com.example.budgetapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetapp.ui.GrowthFragment

class GrowthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_growth)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.growth_fragment_container, GrowthFragment())
                .commit()
        }
    }
}