package com.example.ev2_master_martini.model

/** Receta de demostración con ingredientes, preparación y productos de marca. */
data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val preparation: String,
    val products: List<String>
)
