package com.example.projetkotlin.Files

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.projetkotlin.Database.dbContext
import com.example.projetkotlin.MainActivity
import com.example.projetkotlin.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_recherche_entreprise.*
import java.text.SimpleDateFormat
import java.util.*

class RechercheNomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recherche_entreprise)

        // Purge au bout de trois mois
        val db = dbContext.getDatabase(this)
        val rechercheDao = db.RechercheDao()

        val rechercheResult = rechercheDao.getrechercheSup3Month()

        for (i in rechercheResult)
        {

            val sdfM = SimpleDateFormat("yyyy/MM/dd")
            val cal = Calendar.getInstance()
            cal.add(Calendar.MONTH, -3)
            val date = sdfM.format(cal.time)

            if (sdfM.parse(i.date).time < sdfM.parse(date).time) {

                rechercheDao.deleteRecherche(i)

                Toast.makeText(
                    this,
                    "La recherche ${i.nomRecherche} a été supprimée car elle datait de plus de 3 mois",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        findViewById<FloatingActionButton>(R.id.fbSearchNom).setOnClickListener {
            val query = findViewById<EditText>(R.id.edtQueryName).text
            var type:Int? = 0
            var queryExtend:String? = ""

            if (!edtCP.text.toString().isEmpty()){
                type = 1
                queryExtend = edtCP.text.toString()
            }

            if (!edtCD.text.toString().isEmpty()){
                type = 2
                queryExtend = edtCD.text.toString()
            }

            if (query.toString().isEmpty()){
                Toast.makeText(this, "Veuillez saisir le champ !", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("query", query.toString().toUpperCase())
                intent.putExtra("type", type.toString())
                intent.putExtra("queryExtend", queryExtend.toString())

                query.clear()
                edtCD.text!!.clear()
                edtCP.text!!.clear()
                startActivity(intent)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_historique -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}