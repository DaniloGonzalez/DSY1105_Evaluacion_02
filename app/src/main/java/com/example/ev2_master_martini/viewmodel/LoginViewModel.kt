package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.model.LoginUIState
import com.example.ev2_master_martini.model.StartDestination
import com.example.ev2_master_martini.util.PasswordUtils
import com.example.ev2_master_martini.util.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * 1. Recibe el repositorio por constructor (inyección de dependencias).
 * 2. Tras un login exitoso, decide el destino (Home o Intereses) según los
 *    intereses guardados, de modo que el NavHost ya no lea la persistencia.
 */
class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(email = value, emailError = null, loginError = null)
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(password = value, passwordError = null, loginError = null)
        }
    }

    fun login() {
        if (!validate()) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, loginError = null)
            }

            try {
                val account = userRepository.getAccount().first()
                val current = _uiState.value

                if (
                    account != null &&
                    account.email.equals(current.email.trim(), ignoreCase = true) &&
                    PasswordUtils.matches(current.password, account.password)
                ) {
                    userRepository.setSessionActive(true)

                    val hasInterests = userRepository.getInterests().first()
                        ?.catalogOptions
                        ?.isNotEmpty() == true

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            postLoginDestination = if (hasInterests) {
                                StartDestination.Home
                            } else {
                                StartDestination.Interests
                            }
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginError = "Correo o contraseña incorrectos"
                        )
                    }
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginError = "No se pudo iniciar sesión"
                    )
                }
            }
        }
    }

    private fun validate(): Boolean {
        val current = _uiState.value

        val emailError = when {
            current.email.isBlank() -> "Ingresa tu correo"
            !ValidationUtils.isValidEmail(current.email) ->
                "Ingresa un correo válido"
            else -> null
        }

        val passwordError = when {
            current.password.isBlank() -> "Ingresa tu contraseña"
            !ValidationUtils.isValidPassword(current.password) ->
                "Usa 8 caracteres, letras y números"
            else -> null
        }

        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return emailError == null && passwordError == null
    }
}
