package com.example.ev2_master_martini.model

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val rut: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val addressError: String? = null,
    val rutError: String? = null,
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val registrationSaved: Boolean = false
)
