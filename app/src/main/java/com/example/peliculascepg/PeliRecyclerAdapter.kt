package com.example.peliculascepg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeliRecyclerAdapter(
    private var lista: List<PeliculaUI>,
    private val onClick: (PeliculaUI) -> Unit
) : RecyclerView.Adapter<PeliRecyclerAdapter.PeliVH>() {

    class PeliVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.itemNombre)
        val tvGenero: TextView = itemView.findViewById(R.id.itemGenero)
        val tvAnio: TextView = itemView.findViewById(R.id.itemAño)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliVH {
        // OJO: aquí inflamos tu item.xml (R.layout.item)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return PeliVH(view)
    }

    override fun onBindViewHolder(holder: PeliVH, position: Int) {
        val peli = lista[position]
        holder.tvNombre.text = peli.nombre
        holder.tvGenero.text = peli.genero
        holder.tvAnio.text = peli.anio

        holder.itemView.setOnClickListener { onClick(peli) }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<PeliculaUI>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}