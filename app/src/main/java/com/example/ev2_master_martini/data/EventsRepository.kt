package com.example.ev2_master_martini.data

import com.example.ev2_master_martini.model.EventInfo

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Los eventos dejan de estar incrustados en EventsScreen, EventDetailScreen y
 * HomeViewModel, y pasan a la capa de datos. Se unifican los identificadores
 * para evitar inconsistencias entre secciones (antes Home usaba
 * "fecha_pasteleria" y la pantalla de eventos usaba "feria_pasteleria").
 *
 * Los datos son de demostración (ficticios), según el caso Master Martini.
 */
class EventsRepository {

    /** Devuelve la lista de eventos. */
    fun getEvents(): List<EventInfo> = events

    /**
     * Busca un evento por su id. Si no existe, devuelve el primero,
     * replicando el comportamiento anterior (pantalla de detalle sin vacíos).
     */
    fun getEventById(id: String): EventInfo =
        events.firstOrNull { it.id == id } ?: events.first()

    private companion object {
        val events = listOf(
            EventInfo(
                id = "taller_chocolateria",
                title = "Taller de chocolatería",
                date = "15 de noviembre",
                location = "Santiago, Chile",
                description = "Participa en un taller práctico para aprender nuevas técnicas " +
                    "de trabajo con chocolate.",
                availability = "Cupos disponibles"
            ),
            EventInfo(
                id = "novedades_productos",
                title = "Novedades de productos",
                date = "20 de noviembre",
                location = "Centro de demostraciones Master Martini",
                description = "Conoce los nuevos productos e insumos disponibles para tus " +
                    "preparaciones.",
                availability = "Actividad abierta"
            ),
            EventInfo(
                id = "feria_pasteleria",
                title = "Feria de pastelería",
                date = "5 de diciembre",
                location = "Santiago, Chile",
                description = "Encuentro con proveedores y especialistas del rubro gastronómico.",
                availability = "Inscripción abierta"
            )
        )
    }
}
