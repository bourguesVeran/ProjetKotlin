package com.example.projetkotlin.Class

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Recherche(@PrimaryKey(autoGenerate = true) var id:Long? = null,
                     var id_entreprise:Long?= null,
                     var url : String?= null,
                     var date: String?= null,
                     var activite_principale: String?= null,
                     var total_results: Int?= null,
                     var queryExtend: String ? = null,
                     var typeExtend: String ? = null,
                     var nomRecherche: String?= null):Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recherche

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {

        if (queryExtend != null)
        {
            return "$date : $nomRecherche $queryExtend"
        } else if (nomRecherche != null) {
            return "$date : $nomRecherche"
        } else {
            return "$date"
        }


    }


}