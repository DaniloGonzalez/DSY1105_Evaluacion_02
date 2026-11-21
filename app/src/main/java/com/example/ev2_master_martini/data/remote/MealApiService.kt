package com.example.ev2_master_martini.data.remote

import com.example.ev2_master_martini.data.remote.dto.CategoriesResponseDto
import com.example.ev2_master_martini.data.remote.dto.MealResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    /** Busca recetas por nombre. Ej: search.php?s=chocolate */
    @GET("api/json/v1/1/search.php")
    suspend fun searchByName(@Query("s") name: String): MealResponseDto

    /** Lista todas las categorías. Ej: categories.php */
    @GET("api/json/v1/1/categories.php")
    suspend fun getCategories(): CategoriesResponseDto

    /** Filtra recetas por categoría. Ej: filter.php?c=Seafood */
    @GET("api/json/v1/1/filter.php")
    suspend fun filterByCategory(@Query("c") category: String): MealResponseDto

    /** Devuelve una receta aleatoria. Ej: random.php */
    @GET("api/json/v1/1/random.php")
    suspend fun getRandomMeal(): MealResponseDto

    /** Obtiene el detalle completo de una receta por id. Ej: lookup.php?i=52772 */
    @GET("api/json/v1/1/lookup.php")
    suspend fun lookupById(@Query("i") id: String): MealResponseDto
}
