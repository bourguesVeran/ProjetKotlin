package com.example.projetkotlin.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projetkotlin.Class.Entreprise
import com.example.projetkotlin.Class.Jointure
import com.example.projetkotlin.Class.Recherche
import com.example.projetkotlin.DAO.EntrepriseDao
import com.example.projetkotlin.DAO.JointureDao
import com.example.projetkotlin.DAO.RechercheDao

@Database(entities = [Entreprise::class, Jointure::class, Recherche::class], version = 1)
abstract class dbContext: RoomDatabase() {

    abstract fun Entreprisedao(): EntrepriseDao
    abstract fun JointureDao(): JointureDao
    abstract fun RechercheDao(): RechercheDao

    companion object {
        private var INSTANCE: dbContext? = null
        @JvmStatic fun getDatabase(context: Context): dbContext {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, dbContext::class.java, "context.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }

}