package com.example.tp3_basededonnes
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Planning::class], version = 2, exportSchema = false) // On passe en version 2
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun planningDao(): PlanningDao // Ajout du DAO Planning

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tp3_database"
                )
                    .fallbackToDestructiveMigration() // Important car on a changé la version
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}