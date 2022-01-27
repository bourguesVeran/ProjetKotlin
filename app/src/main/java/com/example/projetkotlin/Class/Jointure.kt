package com.example.projetkotlin.Class

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    indices = [ Index(value = ["id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Entreprise::class,
            parentColumns = ["id"],
            childColumns = ["id_entreprise"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Recherche::class,
            parentColumns = ["id"],
            childColumns = ["id_recherche"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Jointure(@PrimaryKey(autoGenerate = true) var id:Long? = null,
                    val id_entreprise: Long,
                    val id_recherche: Long): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Jointure

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "id_entreprise=$id_entreprise id_recherche=$id_recherche"
    }


}