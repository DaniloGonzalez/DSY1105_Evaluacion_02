package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.InspirationRepository
import com.example.ev2_master_martini.model.InspirationDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * CAMBIO REALIZADO (integración de API externa - TheMealDB):
 * Carga el detalle completo de una receta con lookup.php?i={id}.
 */
class InspirationDetailViewModel(
    private val inspirationRepository: InspirationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InspirationDetailUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    fun load(mealId: String) {
        if (mealId.isBlank()) {
            _uiState.update {
                it.copy(isLoading = false, errorMessage = "Receta no válida.")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            inspirationRepository.getRecipeById(mealId)
                .onSuccess { recipe ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            recipe = recipe,
                            errorMessage = if (recipe == null) {
                                "No se encontró la receta."
                            } else {
                                null
                            }
                        )
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "No se pudo cargar la receta. Revisa tu conexión."
                        )
                    }
                }
        }
    }
}
