package com.example.tp3_basededonnes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlanningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanning(planning: Planning)

    @Query("SELECT * FROM planning_table WHERE userLogin = :login")
    suspend fun getPlanningByLogin(login: String): Planning?
}