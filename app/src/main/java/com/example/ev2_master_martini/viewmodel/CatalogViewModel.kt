package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ev2_master_martini.data.CatalogRepository
import com.example.ev2_master_martini.model.CatalogCategory
import com.example.ev2_master_martini.model.CatalogUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * La pantalla de catálogo ya no filtra ni conoce los datos; el filtrado por
 * categoría queda en el ViewModel apoyado en [CatalogRepository].
 */
class CatalogViewModel(
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CatalogUiState(items = catalogRepository.getCatalog())
    )
    val uiState = _uiState.asStateFlow()

    fun onCategorySelected(category: CatalogCategory) {
        _uiState.update { current ->
            current.copy(
                selectedCategory = category,
                items = catalogRepository.getByCategory(category)
            )
        }
    }
}
