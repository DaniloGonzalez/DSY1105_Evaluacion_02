package com.example.ev2_master_martini.data

import com.example.ev2_master_martini.model.HomeEvent
import com.example.ev2_master_martini.model.RecommendedContent

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * La lógica de negocio del inicio (armado de recomendaciones y eventos) sale
 * del HomeViewModel y queda en la capa de datos.
 */
class HomeRepository(
    private val eventsRepository: EventsRepository
) {

    /**
     * Genera recomendaciones según las preferencias del usuario. Si no hay
     * preferencias registradas, se devuelven recomendaciones generales para que
     * la pantalla nunca quede vacía.
     */
    fun getRecommendations(preferences: Set<String>): List<RecommendedContent> {
        val preferred = catalog.filter { content ->
            preferences.any { preference ->
                preference.lowercase() in content.title.lowercase() ||
                    content.category.lowercase() == preference.lowercase()
            }
        }

        return if (preferred.isNotEmpty()) preferred else catalog.take(3)
    }

    /** Eventos de la marca transformados al modelo que consume el inicio. */
    fun getEvents(): List<HomeEvent> =
        eventsRepository.getEvents().map { event ->
            HomeEvent(
                id = event.id,
                title = event.title,
                availability = event.availability,
                date = event.date
            )
        }

    private companion object {
        val catalog = listOf(
            RecommendedContent("curso_chocolateria", "Curso de chocolatería", "Cursos", "thumb_curso"),
            RecommendedContent("recetas_basicas", "Recetas para pastelería", "Recetas", "thumb_receta"),
            RecommendedContent("videos_tecnicos", "Videos técnicos de horneo", "Videos técnicos", "thumb_video"),
            RecommendedContent("curso_pasteleria", "Curso de pastelería de vitrina", "Cursos", "thumb_curso"),
            RecommendedContent("recetas_galletas", "Recetas de galletas y empanadas", "Recetas", "thumb_receta"),
            RecommendedContent("videos_chocolateria", "Videos de chocolatería", "Videos técnicos", "thumb_video")
        )
    }
}
