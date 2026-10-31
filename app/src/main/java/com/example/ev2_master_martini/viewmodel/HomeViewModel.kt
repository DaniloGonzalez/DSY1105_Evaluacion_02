package com.example.ev2_master_martini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.model.HomeEvent
import com.example.ev2_master_martini.model.HomeUiState
import com.example.ev2_master_martini.model.RecommendedContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            val account = userRepository.getAccount().first()
            val interests = userRepository.getInterests().first()

            val userName = account?.name.orEmpty()
            val preferences = interests?.catalogOptions.orEmpty()

            _uiState.value = HomeUiState(
                isLoading = false,
                userName = userName,
                businessType = interests?.businessType.orEmpty(),
                preferredCategories = preferences,
                recommendations = buildRecommendations(preferences),
                events = mockEvents()
            )
        }
    }

    /**
     * Genera recomendaciones según las preferencias del usuario. Se usa un
     * catálogo de ejemplo con datos ficticios (requisito del caso). Si el
     * usuario no tiene preferencias registradas, se muestran recomendaciones
     * generales para que la pantalla nunca quede vacía.
     */
    private fun buildRecommendations(preferences: Set<String>): List<RecommendedContent> {
        val catalog = listOf(
            RecommendedContent("curso_chocolateria", "Curso de chocolatería", "Cursos", "thumb_curso"),
            RecommendedContent("recetas_basicas", "Recetas para pastelería", "Recetas", "thumb_receta"),
            RecommendedContent("videos_tecnicos", "Videos técnicos de horneo", "Videos técnicos", "thumb_video"),
            RecommendedContent("curso_pasteleria", "Curso de pastelería de vitrina", "Cursos", "thumb_curso"),
            RecommendedContent("recetas_galletas", "Recetas de galletas y empanadas", "Recetas", "thumb_receta"),
            RecommendedContent("videos_chocolateria", "Videos de chocolatería", "Videos técnicos", "thumb_video")
        )

        val preferred = catalog.filter { content ->
            preferences.any { preference ->
                preference.lowercase() in content.title.lowercase() ||
                        content.category.lowercase() == preference.lowercase()
            }
        }

        return if (preferred.isNotEmpty()) preferred else catalog.take(3)
    }

    /** Lista de eventos y novedades de ejemplo (mock data, datos ficticios). */
    private fun mockEvents(): List<HomeEvent> {
        return listOf(
            HomeEvent("taller_chocolateria", "Taller de chocolatería", "Cupos disponibles", "15 de noviembre"),
            HomeEvent("novedades_productos", "Novedades de productos", "Actividad abierta", "20 de noviembre"),
            HomeEvent("fecha_pasteleria", "Feria de pastelería", "Inscripción abierta", "5 de diciembre")
        )
    }
}