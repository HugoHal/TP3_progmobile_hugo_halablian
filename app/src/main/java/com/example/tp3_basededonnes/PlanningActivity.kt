package com.example.tp3_basededonnes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class PlanningActivity : AppCompatActivity() {

    // On déclare la base de données ici pour qu'elle soit accessible partout dans la classe
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning)

        // INITIALISATION de la base de données (l'étape qui manquait)
        database = AppDatabase.getDatabase(this)

        val login = intent.getStringExtra("user_login") ?: ""
        findViewById<TextView>(R.id.tvTitrePlanning).text = "Planning de $login"

        val et8h = findViewById<EditText>(R.id.et0810)
        val et10h = findViewById<EditText>(R.id.et1012)
        val et14h = findViewById<EditText>(R.id.et1416)
        val et16h = findViewById<EditText>(R.id.et1618)
        val btnSave = findViewById<Button>(R.id.btnSavePlanning)

        btnSave.setBackgroundColor(resources.getColor(R.color.sage_green))

        btnSave.setOnClickListener {
            val planning = Planning(
                userLogin = login,
                creneau8h10h = et8h.text.toString(),
                creneau10h12h = et10h.text.toString(),
                creneau14h16h = et14h.text.toString(),
                creneau16h18h = et16h.text.toString()
            )

            // Dans btnSave.setOnClickListener
            lifecycleScope.launch {
                database.planningDao().insertPlanning(planning)

                // On change de page pour la synthèse
                val intent = Intent(this@PlanningActivity, SynthesePlanningActivity::class.java)
                intent.putExtra("user_login", login)
                startActivity(intent)
            }
        }
    }

    // Petite fonction pour afficher la synthèse proprement
    private fun showSynthese(p: Planning) {
        val msg = "Synthèse :\n8h: ${p.creneau8h10h}\n10h: ${p.creneau10h12h}\n14h: ${p.creneau14h16h}\n16h: ${p.creneau16h18h}"
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}