package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ev2_master_martini.data.AppContainer

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Fábrica de ViewModel que inyecta las dependencias desde el [AppContainer].
 * Evita que cada ViewModel construya repositorios a mano y permite sustituir
 * los repositorios por dobles de prueba en las pruebas unitarias.
 */
class ViewModelFactory(
    private val container: AppContainer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel: ViewModel = when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) ->
                SplashViewModel(container.userRepository)

            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(container.userRepository)

            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(container.userRepository)

            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                RegisterViewModel(container.userRepository)

            modelClass.isAssignableFrom(InterestsViewModel::class.java) ->
                InterestsViewModel(container.userRepository)

            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(container.userRepository, container.homeRepository)

            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(container.userRepository)

            modelClass.isAssignableFrom(CatalogViewModel::class.java) ->
                CatalogViewModel(container.catalogRepository)

            modelClass.isAssignableFrom(RecipesViewModel::class.java) ->
                RecipesViewModel(container.recipesRepository)

            modelClass.isAssignableFrom(EventsViewModel::class.java) ->
                EventsViewModel(container.eventsRepository)

            else -> throw IllegalArgumentException(
                "ViewModel no soportado: ${modelClass.name}"
            )
        }

        return viewModel as T
    }
}
