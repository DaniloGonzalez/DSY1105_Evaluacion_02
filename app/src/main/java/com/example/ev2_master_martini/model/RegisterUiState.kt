package com.example.ev2_master_martini.model

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val phoneCountryCode: String = "+56",
    val phoneNumber: String = "",
    val address: String = "",
    val rut: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val addressError: String? = null,
    val rutError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val registrationSaved: Boolean = false
) {
    val phone: String
        get() = "$phoneCountryCode$phoneNumber"
}
