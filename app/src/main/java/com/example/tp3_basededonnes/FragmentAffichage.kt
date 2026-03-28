package com.example.tp3_basededonnes

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class FragmentAffichage : Fragment(R.layout.fragment_affichage) {

    private lateinit var database: AppDatabase

    companion object {
        // Méthode pour passer le login du nouveau fragment
        fun newInstance(login: String): FragmentAffichage {
            val args = Bundle()
            args.putString("user_login", login)
            val fragment = FragmentAffichage()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = AppDatabase.getDatabase(requireContext())
        val tvInfo = view.findViewById<TextView>(R.id.tvDisplayInfo)
        val btnRetour = view.findViewById<Button>(R.id.btnRetour)

        // Récupération du login transmis
        val login = arguments?.getString("user_login") ?: ""

        // Recherche de l'utilisateur en base de données
        lifecycleScope.launch {
            val user = database.userDao().getUserByLogin(login)

            user?.let {
                tvInfo.text = """
                    👤 Login : ${it.login}
                    📝 Nom : ${it.nom}
                    👋 Prénom : ${it.prenom}
                    📧 Email : ${it.email}
                    📞 Tel : ${it.telephone}
                    🎂 Né(e) le : ${it.dateNaissance}
                    🌟 Intérêts : ${it.centresInteret}
                """.trimIndent()
            }
        }

        // Bouton pour revenir à l'inscription
        btnRetour.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}