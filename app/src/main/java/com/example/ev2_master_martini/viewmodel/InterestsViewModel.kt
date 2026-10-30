package com.example.ev2_master_martini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.model.InterestsUiState
import com.example.ev2_master_martini.model.UserInterests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InterestsViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    private val _uiState = MutableStateFlow(InterestsUiState())
    val uiState = _uiState.asStateFlow()

    fun onBusinessTypeSelected(value: String) {
        _uiState.update {
            it.copy(businessType = value)
        }
    }

    fun onBusinessSizeSelected(value: String) {
        _uiState.update {
            it.copy(businessSize = value)
        }
    }

    fun onCatalogOptionToggle(option: String) {
        _uiState.update { current ->
            val updatedOptions = current.selectedCatalogOptions.toMutableSet()

            if (!updatedOptions.add(option)) {
                updatedOptions.remove(option)
            }

            current.copy(
                selectedCatalogOptions = updatedOptions,
                catalogError = null
            )
        }
    }

    fun saveInterests() {
        if (!validate()) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isSaving = true, saveError = null)
            }

            try {
                val current = _uiState.value

                userRepository.saveInterests(
                    UserInterests(
                        businessType = current.businessType,
                        businessSize = current.businessSize,
                        catalogOptions = current.selectedCatalogOptions
                    )
                )

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        interestsSaved = true
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        saveError = "No se pudieron guardar tus preferencias"
                    )
                }
            }
        }
    }

    private fun validate(): Boolean {
        val error = if (_uiState.value.selectedCatalogOptions.isEmpty()) {
            "Selecciona al menos una opción del catálogo"
        } else {
            null
        }

        _uiState.update {
            it.copy(catalogError = error)
        }

        return error == null
    }
}
