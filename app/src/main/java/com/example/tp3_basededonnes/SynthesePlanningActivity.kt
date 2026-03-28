package com.example.tp3_basededonnes

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SynthesePlanningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synthese_planning)

        val login = intent.getStringExtra("user_login") ?: ""
        val database = AppDatabase.getDatabase(this)
        val tvDisplay = findViewById<TextView>(R.id.tvSynthesePlanning)

        lifecycleScope.launch {
            val p = database.planningDao().getPlanningByLogin(login)
            p?.let {
                tvDisplay.text = """
                    📅 08h-10h : ${it.creneau8h10h}
                    
                    📅 10h-12h : ${it.creneau10h12h}
                    
                    📅 14h-16h : ${it.creneau14h16h}
                    
                    📅 16h-18h : ${it.creneau16h18h}
                """.trimIndent()
            }
        }

        findViewById<Button>(R.id.btnQuitter).setOnClickListener { finishAffinity() }
    }
}