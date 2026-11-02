package com.example.ev2_master_martini.model

/** Contenido recomendado para el dashboard personalizado. */
data class RecommendedContent(
    val id: String,
    val title: String,
    val category: String,
    val thumbnailRes: String
)

/** Evento o novedad de la marca para el carrusel/panel del Home. */
data class HomeEvent(
    val id: String,
    val title: String,
    val availability: String,
    val date: String
)

/** Estado completo de la pantalla de inicio. */
data class HomeUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val businessType: String = "",
    val preferredCategories: Set<String> = emptySet(),
    val recommendations: List<RecommendedContent> = emptyList(),
    val events: List<HomeEvent> = emptyList()
)