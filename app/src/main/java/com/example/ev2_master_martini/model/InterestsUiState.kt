package com.example.ev2_master_martini.model

data class InterestsUiState(
    val businessType: String = "",
    val businessSize: String = "",
    val selectedCatalogOptions: Set<String> = emptySet(),
    val businessTypeError: String? = null,
    val businessSizeError: String? = null,
    val catalogError: String? = null,
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val interestsSaved: Boolean = false
)
