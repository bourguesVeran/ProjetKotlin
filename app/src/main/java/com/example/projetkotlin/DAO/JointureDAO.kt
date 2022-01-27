package com.example.projetkotlin.DAO

import androidx.room.*
import com.example.projetkotlin.Class.Jointure


@Dao
interface JointureDao {

    @Query("SELECT * FROM jointure")
    fun getAllJointure(): List<Jointure>

    @Query("Select count(*) from jointure")
    fun countJointure(): Int

    @Query("SELECT * FROM jointure WHERE id=:param")
    fun getJointureById(param: Long): Jointure

    @Query("SELECT id_entreprise FROM jointure Where id_recherche = :param ")
    fun getListEntreprise(param: Long): List<Long>

    @Insert
    fun insertJointure(jointure: Jointure): Long

    @Update
    fun updateJointure(jointure: Jointure)

    @Delete
    fun deleteJointure(jointure: Jointure)

}