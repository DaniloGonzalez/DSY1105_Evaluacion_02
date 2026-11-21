package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.HomeRepository
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * El ViewModel ya no construye el repositorio ni define las reglas de negocio:
 * recibe sus dependencias por constructor y delega el armado de
 * recomendaciones/eventos a [HomeRepository].
 */
class HomeViewModel(
    private val userRepository: UserRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            val account = userRepository.getAccount().first()
            val interests = userRepository.getInterests().first()

            val preferences = interests?.catalogOptions.orEmpty()

            _uiState.value = HomeUiState(
                isLoading = false,
                userName = account?.name.orEmpty(),
                businessType = interests?.businessType.orEmpty(),
                preferredCategories = preferences,
                recommendations = homeRepository.getRecommendations(preferences),
                events = homeRepository.getEvents()
            )
        }
    }
}
