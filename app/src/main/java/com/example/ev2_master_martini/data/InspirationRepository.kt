package com.example.ev2_master_martini.data

import com.example.ev2_master_martini.data.remote.MealApiService
import com.example.ev2_master_martini.data.remote.dto.MealDto
import com.example.ev2_master_martini.data.remote.dto.MealResponseDto
import com.example.ev2_master_martini.model.InspirationIngredient
import com.example.ev2_master_martini.model.InspirationRecipe

class InspirationRepository(
    private val api: MealApiService
) {

    suspend fun searchByName(name: String): Result<List<InspirationRecipe>> =
        runCatching { api.searchByName(name).toRecipes() }

    suspend fun getCategories(): Result<List<String>> =
        runCatching {
            api.getCategories().categories.orEmpty()
                .mapNotNull { category ->
                    category.strCategory?.takeIf { it.isNotBlank() }
                }
        }

    suspend fun filterByCategory(category: String): Result<List<InspirationRecipe>> =
        runCatching { api.filterByCategory(category).toRecipes() }

    suspend fun getRandomMeal(): Result<InspirationRecipe?> =
        runCatching { api.getRandomMeal().toRecipes().firstOrNull() }

    suspend fun getRecipeById(id: String): Result<InspirationRecipe?> =
        runCatching { api.lookupById(id).toRecipes().firstOrNull() }
}

private fun MealResponseDto.toRecipes(): List<InspirationRecipe> =
    meals.orEmpty().map { it.toRecipe() }

private fun MealDto.toRecipe(): InspirationRecipe = InspirationRecipe(
    id = idMeal.orEmpty(),
    name = strMeal.orEmpty(),
    category = strCategory?.takeIf { it.isNotBlank() },
    area = strArea?.takeIf { it.isNotBlank() },
    thumbnailUrl = strMealThumb?.takeIf { it.isNotBlank() },
    instructions = strInstructions?.takeIf { it.isNotBlank() },
    ingredients = ingredientsOrEmpty(),
    youtubeUrl = strYoutube?.takeIf { it.isNotBlank() }
)

/** Junta los 20 campos numerados de ingrediente/medida en una lista limpia. */
private fun MealDto.ingredientsOrEmpty(): List<InspirationIngredient> {
    val names = listOf(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
        strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20
    )
    val measures = listOf(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
    )

    return names.indices.mapNotNull { index ->
        val name = names[index]?.trim().orEmpty()
        if (name.isBlank()) {
            null
        } else {
            InspirationIngredient(
                name = name,
                measure = measures.getOrNull(index)?.trim().orEmpty()
            )
        }
    }
}
