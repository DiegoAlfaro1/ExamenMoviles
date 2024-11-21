package com.data.repositories

import com.data.network.model.EventoHistorico
import com.data.network.model.ParseService

class MainRepository(private val service: ParseService) {

    fun obtenerHistoricalEvents(callback: (List<EventoHistorico>?, Exception?) -> Unit) {
        service.fetchHistoricalEvents { events, error ->
            callback(events, error)
        }
    }


}
