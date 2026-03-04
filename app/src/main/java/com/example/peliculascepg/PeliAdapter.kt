package com.example.peliculascepg

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PeliAdapter(
    private val context: Activity,
    private val arrayList: ArrayList<Peliculas>
) : ArrayAdapter<Peliculas>(context, R.layout.item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)

        view.findViewById<TextView>(R.id.itemNombre).text = arrayList[position].nombre ?: ""
        view.findViewById<TextView>(R.id.itemGenero).text = arrayList[position].genero ?: ""
        view.findViewById<TextView>(R.id.itemAño).text = arrayList[position].anio ?: ""

        return view
    }
}