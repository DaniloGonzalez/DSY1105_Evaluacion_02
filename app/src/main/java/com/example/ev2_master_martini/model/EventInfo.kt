package com.example.ev2_master_martini.model

/** Evento o novedad de la marca (dato de demostración, ficticio). */
data class EventInfo(
    val id: String,
    val title: String,
    val date: String,
    val location: String,
    val description: String,
    val availability: String
)
