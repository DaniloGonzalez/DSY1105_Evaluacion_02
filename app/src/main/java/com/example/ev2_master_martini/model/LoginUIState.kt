package com.example.ev2_master_martini.model

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val loginError: String? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    // CAMBIO REALIZADO (separación de responsabilidades): destino a mostrar
    // tras un login exitoso, resuelto por el ViewModel.
    val postLoginDestination: StartDestination? = null
)
