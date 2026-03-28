package com.example.tp3_basededonnes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planning_table")
data class Planning(
    @PrimaryKey val userLogin: String, // Un planning par utilisateur
    val creneau8h10h: String,
    val creneau10h12h: String,
    val creneau14h16h: String,
    val creneau16h18h: String
)