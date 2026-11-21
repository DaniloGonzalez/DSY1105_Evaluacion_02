package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ev2_master_martini.data.RecipesRepository
import com.example.ev2_master_martini.model.Recipe

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Las pantallas de recetas consumen el contenido a través de este ViewModel en
 * lugar de tener las recetas incrustadas en el composable.
 */
class RecipesViewModel(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    fun getRecipes(): List<Recipe> = recipesRepository.getRecipes()

    fun getRecipe(recipeId: String): Recipe = recipesRepository.getRecipeById(recipeId)
}
