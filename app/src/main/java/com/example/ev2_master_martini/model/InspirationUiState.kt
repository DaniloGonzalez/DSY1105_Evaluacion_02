package com.example.ev2_master_martini.model

/**
 * CAMBIO REALIZADO (integración de API externa - TheMealDB):
 * Estado de la sección "Inspírate con recetas del mundo" del Home.
 * La UI solo dibuja; el ViewModel decide qué se muestra.
 */
data class InspirationUiState(
    val isLoading: Boolean = true,
    val query: String = "",
    val selectedCategory: String? = null,
    val categories: List<String> = emptyList(),
    val recipes: List<InspirationRecipe> = emptyList(),
    val errorMessage: String? = null
)

/** Estado de la pantalla de detalle de una receta de la API (lookup). */
data class InspirationDetailUiState(
    val isLoading: Boolean = true,
    val recipe: InspirationRecipe? = null,
    val errorMessage: String? = null
)
