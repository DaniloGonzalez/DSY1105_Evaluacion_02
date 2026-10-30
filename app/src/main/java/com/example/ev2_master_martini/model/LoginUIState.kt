package com.example.ev2_master_martini.model

data class LoginUIState (
     val email: String = "",
     val password: String = "",
     val emailError: String? = null,
     val passwordError: String? = null,
     val loginError: String? = null,
     val isLoading: Boolean = false
)