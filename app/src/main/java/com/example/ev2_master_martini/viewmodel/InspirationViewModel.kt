package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.InspirationRepository
import com.example.ev2_master_martini.model.InspirationRecipe
import com.example.ev2_master_martini.model.InspirationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * CAMBIO REALIZADO (integración de API externa - TheMealDB):
 * Orquesta la sección "Inspírate con recetas del mundo" del Home:
 *  - buscador (search.php),
 *  - chips de categoría (categories.php + filter.php),
 *  - receta aleatoria (random.php).
 *
 * El detalle (lookup.php) lo maneja [InspirationDetailViewModel].
 */
class InspirationViewModel(
    private val inspirationRepository: InspirationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InspirationUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadInitial()
    }

    /** Carga inicial: categorías para los chips + una búsqueda por defecto. */
    private fun loadInitial() {
        loadCategories()
        _uiState.update { it.copy(query = DEFAULT_QUERY) }
        load { inspirationRepository.searchByName(DEFAULT_QUERY) }
    }

    fun onQueryChange(value: String) {
        _uiState.update { it.copy(query = value) }
    }

    /** Ejecuta la búsqueda por texto del buscador. */
    fun search() {
        val query = _uiState.value.query.ifBlank { DEFAULT_QUERY }
        _uiState.update { it.copy(query = query, selectedCategory = null) }
        load { inspirationRepository.searchByName(query) }
    }

    /** Selecciona/deselecciona una categoría (toggle) y filtra o vuelve a buscar. */
    fun onCategorySelected(category: String) {
        val newCategory = if (_uiState.value.selectedCategory == category) null else category
        _uiState.update { it.copy(selectedCategory = newCategory) }

        if (newCategory == null) {
            search()
        } else {
            load { inspirationRepository.filterByCategory(newCategory) }
        }
    }

    /** Trae una receta aleatoria y la deja visible en el carrusel. */
    fun surpriseMe() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            inspirationRepository.getRandomMeal()
                .onSuccess { recipe ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            selectedCategory = null,
                            recipes = listOfNotNull(recipe)
                        )
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = GENERIC_ERROR)
                    }
                }
        }
    }

    /** Reintenta la última operación (filtro o búsqueda). */
    fun retry() {
        val category = _uiState.value.selectedCategory
        if (category != null) {
            load { inspirationRepository.filterByCategory(category) }
        } else {
            search()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            inspirationRepository.getCategories()
                .onSuccess { categories ->
                    _uiState.update { it.copy(categories = categories) }
                }
                .onFailure {
                    // Los chips son opcionales: si fallan, la sección sigue usable.
                }
        }
    }

    private fun load(block: suspend () -> Result<List<InspirationRecipe>>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            block()
                .onSuccess { recipes ->
                    _uiState.update { it.copy(isLoading = false, recipes = recipes) }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = GENERIC_ERROR)
                    }
                }
        }
    }

    private companion object {
        const val DEFAULT_QUERY = "chocolate"
        const val GENERIC_ERROR =
            "No se pudo cargar la información. Revisa tu conexión e inténtalo de nuevo."
    }
}
