package com.example.projetkotlin.Class

import androidx.room.Entity
import java.io.Serializable
import androidx.room.PrimaryKey

@Entity
data class Entreprise(@PrimaryKey(autoGenerate = true) var id:Long? = null,
                      var entreVisu: Boolean = false,
                      var id_entreprise: Long,
                      var code_postal : String,
                      var departement: String,
                      var nom_raison_sociale : String,
                      var longitude: String,
                      var latitude: String,
                      var activite_principale: String,
                      var libelle_activite_principale: String,
                      var email: String,
                      var siret: String,
                      var date_creation: String,
                      var l1_normalisee: String): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entreprise

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    //ToString
    override fun toString(): String {

        return "Nom : $nom_raison_sociale \n"

    }

}