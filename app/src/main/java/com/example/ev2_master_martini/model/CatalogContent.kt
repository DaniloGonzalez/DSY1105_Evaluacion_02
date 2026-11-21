package com.example.ev2_master_martini.model

/** Categorías disponibles en el catálogo de contenidos. */
enum class CatalogCategory(val label: String) {
    All("Todos"),
    Courses("Cursos"),
    Recipes("Recetas"),
    Videos("Videos técnicos")
}

/** Ítem de contenido del catálogo (dato de demostración, ficticio). */
data class ContentItem(
    val id: String,
    val title: String,
    val category: CatalogCategory,
    val description: String,
    val thumbnailRes: Int
)

/** Estado de la pantalla de catálogo. */
data class CatalogUiState(
    val selectedCategory: CatalogCategory = CatalogCategory.All,
    val items: List<ContentItem> = emptyList()
)
