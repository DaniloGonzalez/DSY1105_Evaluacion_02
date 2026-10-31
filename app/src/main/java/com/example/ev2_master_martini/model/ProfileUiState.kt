package com.example.ev2_master_martini.model

data class ProfileUiState(
    val isLoading: Boolean = true,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val rut: String = "",
    val businessType: String = "",
    val businessSize: String = "",
    val catalogOptions: Set<String> = emptySet(),
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val addressError: String? = null,
    val rutError: String? = null,
    val isSaving: Boolean = false,
    val saveMessage: String? = null,
    val largeTextEnabled: Boolean = false
)
