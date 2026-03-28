package com.example.tp3_basededonnes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // On définit le layout qui contient le conteneur de fragments
        setContentView(R.layout.activity_main)

        // Au premier lancement, on charge le Fragment d'inscription
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentInscription())
                .commit()
        }
    }
}