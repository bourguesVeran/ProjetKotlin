package com.example.projetkotlin.DAO

import androidx.room.*
import com.example.projetkotlin.Class.Recherche


@Dao
interface RechercheDao {

    @Query("SELECT * FROM recherche ")
    fun getAllRecherche(): List<Recherche>

    @Query("Select count(*) from recherche")
    fun countRecherche(): Int

    @Query("SELECT * FROM recherche WHERE id=:param")
    fun getRechercheById(param: Long): Recherche

    //Exist
    @Query("SELECT * FROM recherche WHERE url=:param")
    fun rechercheExist(param: String): Recherche?

    //Recherche par place dans la liste
    @Query("SELECT * FROM recherche LIMIT 1 OFFSET :position ")
    fun getRechercheByPosition(position: Int): Recherche?

    //Recherche de + de 3mois
    @Query("SELECT * FROM recherche ORDER BY date DESC ")
    fun getrechercheSup3Month(): List<Recherche>

    @Insert
    fun insertRecherche(recherche: Recherche): Long

    @Update
    fun updateRecherche(recherche: Recherche)

    @Delete
    fun deleteRecherche(recherche: Recherche)

    @Query("DELETE FROM recherche")
    fun deleteAllRecherche()

}