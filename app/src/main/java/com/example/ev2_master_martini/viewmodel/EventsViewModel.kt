package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ev2_master_martini.data.EventsRepository
import com.example.ev2_master_martini.model.EventInfo

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Las pantallas de eventos consumen el contenido a través de este ViewModel en
 * lugar de tener los eventos incrustados en el composable.
 */
class EventsViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {

    fun getEvents(): List<EventInfo> = eventsRepository.getEvents()

    fun getEvent(eventId: String): EventInfo = eventsRepository.getEventById(eventId)
}
