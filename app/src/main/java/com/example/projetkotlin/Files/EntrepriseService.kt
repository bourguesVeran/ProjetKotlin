package com.example.projetkotlin.Files

import android.util.JsonReader
import android.util.JsonToken
import com.example.projetkotlin.Class.Entreprise
import com.example.projetkotlin.Class.Jointure
import com.example.projetkotlin.Class.Recherche
import com.example.projetkotlin.DAO.EntrepriseDao
import com.example.projetkotlin.DAO.JointureDao
import com.example.projetkotlin.DAO.RechercheDao
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection

class EntrepriseService() {

    private val serverUrl = "https://entreprise.data.gouv.fr/api/sirene/v1/full_text/%s?per_page=50"

    fun getEntreprise(query: String, typeExtend: String, queryExtends: String, entreprisedao: EntrepriseDao, rechercheDao: RechercheDao, jointureDao: JointureDao): List<Entreprise> {

        val urlBase = String.format(serverUrl, query)
        val queryUrl = "$urlBase&%s"
        var urlFull = ""

        when (typeExtend)
        {
            "1" -> {
                val data = "code_postal=$queryExtends"
                urlFull = String.format(queryUrl, data)
            }
            "2" -> {
                val data = "departement=$queryExtends"
                urlFull = String.format(queryUrl, data)
            }
            else -> {
                urlFull = urlBase
            }
        }

        val urlExistRecherche = rechercheDao.rechercheExist(urlFull)

        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val c = Calendar.getInstance()
        val date = sdf.format(c.time)

        if ((urlExistRecherche != null) && (urlExistRecherche.date == date)) {
            val idsEntreprise = jointureDao.getListEntreprise(urlExistRecherche.id!!)
            val entrepriseResult = mutableListOf<Entreprise>()
            for (i in idsEntreprise) {
                entrepriseResult.add(entreprisedao.getEntrepriseById(i))
            }
            return entrepriseResult
        } else {

            val url = URL(urlFull)
            var conn: HttpsURLConnection? = null
            try {
                conn = url.openConnection() as HttpsURLConnection
                conn.connect()
                val code = conn.responseCode
                if (code != HttpsURLConnection.HTTP_OK) {
                    return emptyList()
                }
                val inputStream = conn.inputStream ?: return emptyList()
                val reader = JsonReader(inputStream.bufferedReader())

                if (typeExtend == "0")
                {
                    rechercheDao.insertRecherche(Recherche(url = url.toString(), date = date, nomRecherche = query))
                } else {
                    rechercheDao.insertRecherche(Recherche(url = url.toString(), date = date, nomRecherche = query, queryExtend = queryExtends, typeExtend = typeExtend))
                }
                val recherche = rechercheDao.rechercheExist(urlFull)

                var id: Long = 0
                var code_postal: String = ""
                var departement: String = ""
                var nom_raison_sociale: String = ""
                var longitude: String = ""
                var latitude: String = ""
                var activite_principale: String = ""
                var libelle_activite_principale: String = ""
                var email: String = ""
                var siret: String = ""
                var date_creation: String? = null
                var l1_normalisee: String? = null
                val results = mutableListOf<Entreprise>()

                reader.beginObject()
                while (reader.hasNext()) {
                    if (reader.nextName() == "etablissement") {
                        reader.beginArray()
                        while (reader.hasNext()) {
                            var existdatabase = false
                            reader.beginObject()
                            while (reader.hasNext()) {
                                when (reader.nextName()) {
                                    "id" -> {

                                        id = reader.nextLong()
                                        if (entreprisedao.existEntreprise(id)) {
                                            existdatabase = true
                                        }

                                    }
                                    "code_postal" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            code_postal = reader.nextNull().toString()
                                        } else {
                                            code_postal = reader.nextString()
                                        }
                                    }
                                    "departement" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            departement = reader.nextNull().toString()
                                        } else {
                                            departement = reader.nextString()
                                        }
                                    }
                                    "nom_raison_sociale" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            nom_raison_sociale = reader.nextNull().toString()
                                        } else {
                                            nom_raison_sociale = reader.nextString()
                                        }
                                    }
                                    "longitude" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            longitude = reader.nextNull().toString()
                                        } else {
                                            longitude = reader.nextString()
                                        }
                                    }
                                    "latitude" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            latitude = reader.nextNull().toString()
                                        } else {
                                            latitude = reader.nextString()
                                        }
                                    }
                                    "activite_principale" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            activite_principale = reader.nextNull().toString()
                                        } else {
                                            activite_principale = reader.nextString()
                                        }
                                    }
                                    "libelle_activite_principale" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            libelle_activite_principale = reader.nextNull().toString()
                                        } else {
                                            libelle_activite_principale = reader.nextString()
                                        }
                                    }
                                    "email" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            email = reader.nextNull().toString()
                                        } else {
                                            email = reader.nextString()
                                        }
                                    }
                                    "siret" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            siret = reader.nextNull().toString()
                                        } else {
                                            siret = reader.nextString()
                                        }
                                    }
                                    "date_creation" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            date_creation = reader.nextNull().toString()
                                        } else {
                                            date_creation = reader.nextString()
                                            if (date_creation != "") {
                                                val day = date_creation?.substring(6..7)
                                                val month = date_creation?.substring(4..5)
                                                val year = date_creation?.substring(0..3)

                                                date_creation = "$year/$month/$day"
                                            }
                                        }
                                    }
                                    "l1_normalisee" -> {
                                        if (reader.peek() == JsonToken.NULL) {
                                            l1_normalisee = reader.nextNull().toString()
                                        } else {
                                            l1_normalisee = reader.nextString()
                                        }
                                    }
                                    else -> reader.skipValue()
                                }
                            }
                            reader.endObject()

                            val entreprise = Entreprise(
                                    id_entreprise = id,
                                    code_postal = code_postal,
                                    departement = departement,
                                    nom_raison_sociale = nom_raison_sociale,
                                    longitude = longitude,
                                    latitude = latitude,
                                    activite_principale = activite_principale,
                                    libelle_activite_principale = libelle_activite_principale,
                                    email = email,
                                    siret = siret,
                                    date_creation = date_creation.toString(),
                                    l1_normalisee = l1_normalisee.toString()
                            )

                            //UPDATE des champs de l'entreprise si elle existe.
                            if (existdatabase) {

                                val entrepriseFind = entreprisedao.getEntrepriseByIdEntreprise(id)

                                entrepriseFind.activite_principale = entreprise.activite_principale
                                entrepriseFind.libelle_activite_principale = entreprise.libelle_activite_principale
                                entrepriseFind.code_postal = entreprise.code_postal
                                entrepriseFind.date_creation = entreprise.date_creation
                                entrepriseFind.departement = entreprise.departement
                                entrepriseFind.email = entreprise.email
                                entrepriseFind.id_entreprise = entreprise.id_entreprise
                                entrepriseFind.l1_normalisee = entreprise.l1_normalisee
                                entrepriseFind.latitude = entreprise.latitude
                                entrepriseFind.longitude = entreprise.longitude
                                entrepriseFind.nom_raison_sociale = entreprise.nom_raison_sociale
                                entrepriseFind.siret = entreprise.siret
                                entreprisedao.updateEntreprise(entrepriseFind)
                                results.add(entrepriseFind)

                                jointureDao.insertJointure(Jointure(id_entreprise = entrepriseFind.id!!, id_recherche = recherche!!.id!!))
                            } else {
                                entreprisedao.insertEntreprise(entreprise)
                                val entrepriseD = entreprisedao.getEntrepriseByIdEntreprise(id)
                                results.add(entrepriseD)
                                jointureDao.insertJointure(Jointure(id_entreprise = entrepriseD.id!!, id_recherche = recherche!!.id!!))
                            }
                        }
                        reader.endArray()

                    } else {
                        reader.skipValue()
                    }
                }
                reader.endObject()
                return results
            } catch (e: IOException) {
                return listOf()
            } finally {
                conn?.disconnect()
            }
        }
    }
}