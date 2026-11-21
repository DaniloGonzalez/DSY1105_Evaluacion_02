package com.example.ev2_master_martini.model

data class InspirationRecipe(
    val id: String,
    val name: String,
    val category: String?,
    val area: String?,
    val thumbnailUrl: String?,
    val instructions: String? = null,
    val ingredients: List<InspirationIngredient> = emptyList(),
    val youtubeUrl: String? = null
)

/** Ingrediente con su medida tal como lo entrega la API. */
data class InspirationIngredient(
    val name: String,
    val measure: String
)
