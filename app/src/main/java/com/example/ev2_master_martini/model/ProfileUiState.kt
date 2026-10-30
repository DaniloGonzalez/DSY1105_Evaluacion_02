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
    val catalogOptions: Set<String> = emptySet()
)
