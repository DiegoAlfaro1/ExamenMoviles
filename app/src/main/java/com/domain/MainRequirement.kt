package com.domain

import com.data.network.model.EventoHistorico
import com.data.repositories.MainRepository

class MainRequirement(private val repository: MainRepository) {

    fun fetchHistoricalEvents(callback: (List<EventoHistorico>?, Exception?) -> Unit) {
        repository.obtenerHistoricalEvents { events, error ->
            callback(events, error)
        }
    }
}

