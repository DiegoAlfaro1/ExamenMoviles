package com.framework.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.data.network.model.EventoHistorico
import com.diegoalfaro.myapplication.R

class MainAdaptador(private var events: List<EventoHistorico>) :
    RecyclerView.Adapter<MainAdaptador.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.tvDate)
        val description: TextView = view.findViewById(R.id.tvDescription)
        val categories: TextView = view.findViewById(R.id.tvCategories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historical_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.date.text = event.date
        holder.description.text = event.description
        holder.categories.text = "${event.category1}, ${event.category2}"
    }

    override fun getItemCount(): Int = events.size

    fun updateData(newEvents: List<EventoHistorico>) {
        events = newEvents
        notifyDataSetChanged() // Refrescar el RecyclerView
    }
}

