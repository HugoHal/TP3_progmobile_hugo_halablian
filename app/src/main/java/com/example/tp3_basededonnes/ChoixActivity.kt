package com.example.tp3_basededonnes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChoixActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix)

        val btnInscription = findViewById<Button>(R.id.btnVersInscription)
        val btnConnexion = findViewById<Button>(R.id.btnVersConnexion)

        // Couleurs Sage Green
        btnInscription.setBackgroundColor(resources.getColor(R.color.sage_green))
        btnConnexion.setBackgroundColor(resources.getColor(R.color.sage_green))

        btnInscription.setOnClickListener {
            // Lance la MainActivity qui contient le FragmentInscription
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnConnexion.setOnClickListener {
            // Lance une nouvelle activité de Login
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}