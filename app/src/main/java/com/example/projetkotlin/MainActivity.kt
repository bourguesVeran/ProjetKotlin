package com.example.projetkotlin

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.projetkotlin.Class.Entreprise
import com.example.projetkotlin.DAO.EntrepriseDao
import com.example.projetkotlin.DAO.JointureDao
import com.example.projetkotlin.DAO.RechercheDao
import com.example.projetkotlin.Database.dbContext
import com.example.projetkotlin.Files.EntrepriseActivity
import com.example.projetkotlin.Files.EntrepriseService
import com.example.projetkotlin.Files.HistoryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    inner class QueryEntreprise(private val context: Context,
                                private val svc: EntrepriseService,
                                private val entrepriseDao: EntrepriseDao,
                                private val rechercheDao: RechercheDao,
                                private val jointureDao: JointureDao,
                                private val listView: ListView): AsyncTask<String, Void, List<Entreprise>>(){

        override fun onPreExecute() {
            listView.visibility = View.INVISIBLE
            tvcountRecherche.visibility = View.GONE
        }

        override fun onPostExecute(result: List<Entreprise>?) {
            listView.adapter = ArrayAdapter<Entreprise>(context, android.R.layout.simple_list_item_1, android.R.id.text1, result!!)

            val count = lstRecherche.count

            if (count > 1)
            {
                tvcountRecherche.text = "$count entreprises"
            } else {
                tvcountRecherche.text = "$count entreprise"
            }

            listView.visibility = View.VISIBLE
            tvcountRecherche.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): List<Entreprise> {

            val query = params[0] ?: return emptyList()
            val typeExtend = params[1]!!
            val queryExtend = params[2]!!
            return svc.getEntreprise(query, typeExtend, queryExtend, entrepriseDao, rechercheDao, jointureDao)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = dbContext.getDatabase(this)

        val entrepriseDao = db.Entreprisedao()
        val rechercheDao = db.RechercheDao()
        val jointureDao = db.JointureDao()
        val svc = EntrepriseService()

        val query = intent.getStringExtra("query")
        val type = intent.getStringExtra("type")
        val queryExtend = intent.getStringExtra("queryExtend")

        QueryEntreprise(this, svc, entrepriseDao, rechercheDao, jointureDao ,lstRecherche).execute(query, type, queryExtend)

        lstRecherche.setOnItemClickListener { _, _, position, _ ->
            val entreprise = lstRecherche.adapter.getItem(position) as Entreprise
            val intent = Intent(this, EntrepriseActivity::class.java)
            intent.putExtra("Entreprise", entreprise)
            startActivity(intent)
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