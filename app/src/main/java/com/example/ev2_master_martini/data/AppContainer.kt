package com.example.ev2_master_martini.data

import android.content.Context
import com.example.ev2_master_martini.data.remote.ApiClient

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Contenedor simple de dependencias (Service Locator). Centraliza la creación
 * de repositorios para que los ViewModel no instancien dependencias a mano.
 * Esto reduce el acoplamiento y facilita el reemplazo de repositorios en
 * pruebas unitarias.
 */
class AppContainer(context: Context) {

    private val appContext = context.applicationContext

    val userRepository: UserRepository = UserRepository(appContext)
    val catalogRepository: CatalogRepository = CatalogRepository()
    val recipesRepository: RecipesRepository = RecipesRepository()
    val eventsRepository: EventsRepository = EventsRepository()
    val homeRepository: HomeRepository = HomeRepository(eventsRepository)

    // CAMBIO REALIZADO (integración de API externa - TheMealDB):
    // Repositorio de la API externa, construido con el servicio Retrofit.
    val inspirationRepository: InspirationRepository =
        InspirationRepository(ApiClient.mealApiService)
}
