package com.example.tp3_basededonnes

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users_table")
data class User(
    @PrimaryKey val login: String, // Le login doit être unique [cite: 154, 156]
    val motDePasse: String, // Doit inclure 6 caractères [cite: 155]
    val nom: String,
    val prenom: String,
    val dateNaissance: String,
    val telephone: String,
    val email: String,
    val centresInteret: String // Stockage des choix (sport, musique...) [cite: 146]
)