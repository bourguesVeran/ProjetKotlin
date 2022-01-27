package com.example.projetkotlin.Files

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetkotlin.Database.dbContext
import com.example.projetkotlin.R
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val db = dbContext.getDatabase(this)
        val rechercheDao = db.RechercheDao()

        val count = rechercheDao.countRecherche()

        if (count > 1)
        {
            tvcounthistorique.text = "$count recherches"
        } else {
            tvcounthistorique.text = "$count recherche"
        }


        val lsthistorique = findViewById<RecyclerView>(R.id.RctHistorique)
        lsthistorique.layoutManager = LinearLayoutManager(this)
        lsthistorique.adapter = HistoriqueAdapter(
            this,
            rechercheDao
        )

    }

    override fun onResume() {
        super.onResume()
        val db = dbContext.getDatabase(this)
        val rechercheDao = db.RechercheDao()

        val count = rechercheDao.countRecherche()

        if (count > 1)
        {
            tvcounthistorique.text = "$count recherches"
        } else {
            tvcounthistorique.text = "$count recherche"
        }
    }

}