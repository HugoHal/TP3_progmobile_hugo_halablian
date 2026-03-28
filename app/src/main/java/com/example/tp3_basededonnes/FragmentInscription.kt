package com.example.tp3_basededonnes

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.tp3_basededonnes.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch

class FragmentInscription : Fragment(R.layout.fragment_inscription) {

    private lateinit var database: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisation de la base de données
        database = AppDatabase.getDatabase(requireContext())

        // Récupération des vues
        val etLogin = view.findViewById<EditText>(R.id.etLogin)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etNom = view.findViewById<EditText>(R.id.etNom)
        val etPrenom = view.findViewById<EditText>(R.id.etPrenom)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etDate = view.findViewById<EditText>(R.id.etDateNaissance)
        val cgInterests = view.findViewById<ChipGroup>(R.id.cgInterests)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)

        // Style du bouton
        btnSubmit.setBackgroundColor(resources.getColor(R.color.sage_green))

        // 1. Gestion du Calendrier pour la date de naissance
        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    etDate.setText(String.format("%02d/%02d/%d", day, month + 1, year))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // 2. Logique du bouton de soumission
        btnSubmit.setOnClickListener {
            val login = etLogin.text.toString().trim()
            val password = etPassword.text.toString()
            val email = etEmail.text.toString().trim()

            // Validation par Regex et longueur
            if (validateInput(login, password, email)) {
                lifecycleScope.launch {
                    // Vérification de l'unicité du login en BDD
                    val existingUser = database.userDao().getUserByLogin(login)

                    if (existingUser != null) {
                        etLogin.error = "Ce login est déjà utilisé"
                        Toast.makeText(context, "Erreur : Login déjà pris", Toast.LENGTH_SHORT).show()
                    } else {
                        // Récupération des centres d'intérêt sélectionnés
                        val interests = getSelectedInterests(cgInterests)

                        // Création de l'objet utilisateur
                        val newUser = User(
                            login = login,
                            motDePasse = password,
                            nom = etNom.text.toString(),
                            prenom = etPrenom.text.toString(),
                            dateNaissance = etDate.text.toString(),
                            telephone = etPhone.text.toString(),
                            email = email,
                            centresInteret = interests
                        )

                        // Insertion en base de données
                        database.userDao().insertUser(newUser)

                        // Navigation vers le fragment d'affichage
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, FragmentAffichage.newInstance(login))
                            .addToBackStack(null) // Permet de revenir en arrière
                            .commit()
                    }
                }
            }
        }
    }

    /**
     * Valide les entrées utilisateur selon les Regex et contraintes métier.
     */
    private fun validateInput(login: String, pass: String, email: String): Boolean {
        // Login : commence par une lettre, alphanumeric, max 10
        val loginRegex = Regex("^[a-zA-Z][a-zA-Z0-9]{0,9}$")
        // Email : format standard
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,}$")

        return when {
            !login.matches(loginRegex) -> {
                Toast.makeText(context, "Login invalide (Lettre au début, max 10)", Toast.LENGTH_SHORT).show()
                false
            }
            pass.length != 6 -> {
                Toast.makeText(context, "Le mot de passe doit faire exactement 6 caractères", Toast.LENGTH_SHORT).show()
                false
            }
            !email.matches(emailRegex) -> {
                Toast.makeText(context, "Format d'email incorrect", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    /**
     * Parcourt le ChipGroup pour extraire les textes des Chips cochés.
     */
    private fun getSelectedInterests(chipGroup: ChipGroup): String {
        val selectedIds = chipGroup.checkedChipIds
        return if (selectedIds.isEmpty()) {
            "Aucun"
        } else {
            selectedIds.map { id ->
                chipGroup.findViewById<Chip>(id).text.toString()
            }.joinToString(", ")
        }
    }
}