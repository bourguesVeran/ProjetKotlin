package com.example.projetkotlin.Files

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetkotlin.DAO.RechercheDao
import com.example.projetkotlin.R

class HistoriqueAdapter (private val context: Context,
                         private val rechercheDao: RechercheDao
): RecyclerView.Adapter<HistoriqueViewHodler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriqueViewHodler {
        return HistoriqueViewHodler(LayoutInflater.from(context).inflate(R.layout.history_card, parent,false))

    }

    override fun onBindViewHolder(holder: HistoriqueViewHodler, position: Int) {

        val recherche = rechercheDao.getRechercheByPosition(position)

        holder.tvNomRecherche.text = recherche?.nomRecherche

        holder.tvDateRecherche.text = recherche?.date


        if (recherche?.queryExtend != null){
            holder.tvQueryExtendRecherche.text = "Recherche avancée : ${recherche.queryExtend}"
        } else {
            holder.tvQueryExtendRecherche.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return rechercheDao.countRecherche()
    }


}