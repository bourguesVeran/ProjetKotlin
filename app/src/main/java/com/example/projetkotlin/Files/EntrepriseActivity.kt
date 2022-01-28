package com.example.projetkotlin.Files

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.projetkotlin.Class.Entreprise
import com.example.projetkotlin.Database.dbContext
import com.example.projetkotlin.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_entreprise.*

class EntrepriseActivity : AppCompatActivity() {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entreprise)

        val entreprise = intent.getSerializableExtra("Entreprise") as Entreprise ?: null

        if (entreprise != null) {

            val db = dbContext.getDatabase(this)

            val entrepriseDao = db.Entreprisedao()

            entreprise.entreVisu = true

            entrepriseDao.updateEntreprise(entreprise)

            tvName.text = String.format(getString(R.string.name), entreprise.nom_raison_sociale)

            if (entreprise.libelle_activite_principale != "kotlin.Unit")
            {
                tvAdresse.text = String.format(getString(R.string.libact), entreprise.libelle_activite_principale)
            }else{
                cardc3.visibility = View.GONE
            }

            tvDate.text = String.format(getString(R.string.date), entreprise.date_creation)
            tvCodePostal.text = String.format(getString(R.string.cp), entreprise.code_postal)
            val latt = entreprise.latitude
            val long = entreprise.longitude

            if (latt == "kotlin.Unit" || long == "kotlin.Unit"){
                Toast.makeText(this, "Cette entreprise ne possède pas de localisation Maps", Toast.LENGTH_SHORT).show()
            } else {
                mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(OnMapReadyCallback {
                    googleMap = it

                    val loc1 = LatLng(latt.toDouble(),long.toDouble())
                    googleMap.addMarker(MarkerOptions().position(loc1).title(entreprise.nom_raison_sociale))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc1,14f))
                })
            }

        }

    }
}