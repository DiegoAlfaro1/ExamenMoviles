package com.framework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.data.network.model.EventoHistorico
import com.domain.MainRequirement

class MainViewModel(private val requirement: MainRequirement) : ViewModel() {

    private val _events = MutableLiveData<List<EventoHistorico>?>()
    val events: MutableLiveData<List<EventoHistorico>?> get() = _events

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun recuperarEventos() {
        requirement.fetchHistoricalEvents { events, error ->
            if (error == null && events != null) {
                _events.postValue(events)
            } else {
                _error.postValue(error?.localizedMessage ?: "Unknown error")
            }
        }
    }

}