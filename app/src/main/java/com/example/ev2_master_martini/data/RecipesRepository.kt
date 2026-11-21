package com.example.ev2_master_martini.data

import com.example.ev2_master_martini.model.Recipe

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Las recetas dejan de estar incrustadas en las pantallas (RecipesScreen y
 * RecipeDetailScreen) y pasan a la capa de datos.
 *
 * Los datos son de demostración (ficticios), según el caso Master Martini.
 */
class RecipesRepository {

    /** Devuelve la lista de recetas disponibles. */
    fun getRecipes(): List<Recipe> = recipes

    /**
     * Busca una receta por su id. Si no existe, devuelve la primera receta,
     * replicando el comportamiento anterior (pantalla de detalle sin vacíos).
     */
    fun getRecipeById(id: String): Recipe =
        recipes.firstOrNull { it.id == id } ?: recipes.first()

    private companion object {
        val recipes = listOf(
            Recipe(
                id = "torta_chocolate",
                title = "Torta de chocolate",
                description = "Receta húmeda y fácil de preparar para pastelería.",
                ingredients = listOf(
                    "200 g de cobertura de chocolate",
                    "250 g de harina",
                    "200 g de azúcar",
                    "4 huevos",
                    "150 ml de leche",
                    "100 g de mantequilla"
                ),
                preparation = "Precalienta el horno a 180 °C. Mezcla los ingredientes secos. " +
                    "Incorpora los huevos, la leche y la mantequilla. Agrega el chocolate " +
                    "derretido, vierte en un molde y hornea durante 40 minutos.",
                products = listOf(
                    "Cobertura de chocolate semiamargo",
                    "Harina 0000 para pastelería",
                    "Polvo de hornear profesional",
                    "Esencia de vainilla"
                )
            ),
            Recipe(
                id = "brownie_clasico",
                title = "Brownie clásico",
                description = "Brownie de chocolate con textura suave y sabor intenso.",
                ingredients = listOf(
                    "200 g de chocolate",
                    "150 g de mantequilla",
                    "180 g de azúcar",
                    "3 huevos",
                    "100 g de harina",
                    "100 g de nueces"
                ),
                preparation = "Derrite el chocolate junto con la mantequilla. Incorpora el azúcar " +
                    "y los huevos. Agrega la harina y las nueces. Vierte la mezcla en un molde " +
                    "y hornea durante 25 minutos a 180 °C.",
                products = listOf(
                    "Cobertura de chocolate premium 70%",
                    "Harina de fuerza para repostería",
                    "Mantequilla sin sal certificada"
                )
            ),
            Recipe(
                id = "mousse_chocolate",
                title = "Mousse de chocolate",
                description = "Postre ligero ideal para presentaciones individuales.",
                ingredients = listOf(
                    "250 g de chocolate",
                    "400 ml de crema",
                    "50 g de azúcar",
                    "3 claras de huevo"
                ),
                preparation = "Derrite el chocolate y déjalo entibiar. Bate la crema y las claras " +
                    "por separado. Integra todo con movimientos envolventes y refrigera durante " +
                    "al menos 2 horas.",
                products = listOf(
                    "Chocolate de cobertura 60%",
                    "Crema de repostería 40% M.G.",
                    "Azúcar flor impalpable"
                )
            )
        )
    }
}
