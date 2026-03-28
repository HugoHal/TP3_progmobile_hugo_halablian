package com.example.tp3_basededonnes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val database = AppDatabase.getDatabase(this)
        val etLogin = findViewById<EditText>(R.id.etLoginLogin)
        val etPass = findViewById<EditText>(R.id.etPasswordLogin)
        val btnValider = findViewById<Button>(R.id.btnLoginSubmit)

        btnValider.setBackgroundColor(resources.getColor(R.color.sage_green))

        btnValider.setOnClickListener {
            val login = etLogin.text.toString()
            val password = etPass.text.toString()

            lifecycleScope.launch {
                val user = database.userDao().getUserByLogin(login)
                // Vérification existence et mot de passe [cite: 22, 24]
                if (user != null && user.motDePasse == password) {
                    val intent = Intent(this@LoginActivity, PlanningActivity::class.java)
                    intent.putExtra("user_login", login)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Erreur : Login ou mot de passe incorrect", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}