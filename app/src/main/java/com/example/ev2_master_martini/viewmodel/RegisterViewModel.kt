package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ev2_master_martini.model.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) {
        _uiState.update {
            it.copy(
                name = value,
                nameError = null
            )
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(
                email = value,
                emailError = null
            )
        }
    }

    fun onPhoneChange(value: String) {
        _uiState.update {
            it.copy(
                phone = value,
                phoneError = null
            )
        }
    }

    fun onAddressChange(value: String) {
        _uiState.update {
            it.copy(
                address = value,
                addressError = null
            )
        }
    }

    fun onRutChange(value: String) {
        _uiState.update {
            it.copy(
                rut = value,
                rutError = null
            )
        }
    }

    fun validate(): Boolean {
        val current = _uiState.value

        val nameError = if (current.name.isBlank()) {
            "Ingresa tu nombre"
        } else {
            null
        }

        val emailError = when {
            current.email.isBlank() -> "Ingresa tu correo"
            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(current.email)
                .matches() -> "Ingresa un correo válido"
            else -> null
        }

        val phoneError = if (current.phone.isBlank()) {
            "Ingresa tu teléfono"
        } else {
            null
        }

        val addressError = if (current.address.isBlank()) {
            "Ingresa tu dirección"
        } else {
            null
        }

        val rutError = if (current.rut.isBlank()) {
            "Ingresa el RUT de empresa"
        } else {
            null
        }

        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                phoneError = phoneError,
                addressError = addressError,
                rutError = rutError
            )
        }

        return listOf(
            nameError,
            emailError,
            phoneError,
            addressError,
            rutError
        ).all { it == null }
    }
}
