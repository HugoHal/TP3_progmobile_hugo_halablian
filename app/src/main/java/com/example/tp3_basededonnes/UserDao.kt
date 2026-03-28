package com.example.tp3_basededonnes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    // Permet d'insérer un utilisateur.
    // Si le login existe déjà, il sera remplacé[cite: 147, 156].
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // Permet de récupérer un utilisateur par son login pour la connexion[cite: 159].
    @Query("SELECT * FROM users_table WHERE login = :login LIMIT 1")
    suspend fun getUserByLogin(login: String): User?

    // Permet de récupérer tous les utilisateurs (utile pour les tests)
    @Query("SELECT * FROM users_table")
    suspend fun getAllUsers(): List<User>
}