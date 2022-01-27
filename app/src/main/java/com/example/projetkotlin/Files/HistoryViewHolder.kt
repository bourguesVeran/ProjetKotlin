package com.example.projetkotlin.Files

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetkotlin.R

class HistoriqueViewHodler(card: View): RecyclerView.ViewHolder(card) {

    val tvNomRecherche = card.findViewById<TextView>(R.id.tvnamerecherche)
    val tvQueryExtendRecherche = card.findViewById<TextView>(R.id.tvqueryextendrecherche)
    val tvDateRecherche = card.findViewById<TextView>(R.id.tvdaterecherche)
    val card = card.findViewById<CardView>(R.id.card)

}