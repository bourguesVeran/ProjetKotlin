package com.example.projetkotlin.DAO

import androidx.room.*
import com.example.projetkotlin.Class.Entreprise


@Dao
interface EntrepriseDao {

    @Query("SELECT * FROM entreprise")
    fun getAllEntreprise(): List<Entreprise>

    @Query("Select count(*) from entreprise where id=:id_entrepise")
    fun countEntreprise(id_entrepise: Long): Int

    @Query("Select * from entreprise where id_entreprise=:id_entrepise")
    fun existEntreprise(id_entrepise: Long): Boolean

    @Query("SELECT * FROM entreprise WHERE id=:param")
    fun getEntrepriseById(param: Long): Entreprise

    @Query("SELECT * FROM entreprise WHERE id_entreprise=:id_entrepise")
    fun getEntrepriseByIdEntreprise(id_entrepise: Long): Entreprise

    @Insert
    fun insertEntreprise(entreprise: Entreprise): Long

    @Update
    fun updateEntreprise(entreprise: Entreprise)

    @Delete
    fun deleteEntreprise(entreprise: Entreprise)

    @Query("DELETE FROM entreprise")
    fun deleteAllEntreprise()
}