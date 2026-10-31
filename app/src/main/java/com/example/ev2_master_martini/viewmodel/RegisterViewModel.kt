package com.example.ev2_master_martini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.model.RegisterUiState
import com.example.ev2_master_martini.model.UserAccount
import com.example.ev2_master_martini.util.PasswordUtils
import com.example.ev2_master_martini.util.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) =
        updateState { copy(name = value, nameError = null) }

    fun onEmailChange(value: String) =
        updateState { copy(email = value, emailError = null) }

    fun onPhoneCountryCodeChange(value: String) =
        updateState { copy(phoneCountryCode = value, phoneError = null) }

    fun onPhoneNumberChange(value: String) =
        updateState { copy(phoneNumber = value.filter { it.isDigit() }, phoneError = null) }

    fun onAddressChange(value: String) =
        updateState { copy(address = value, addressError = null) }

    fun onRutChange(value: String) =
        updateState { copy(rut = value, rutError = null) }

    fun onPasswordChange(value: String) =
        updateState { copy(password = value, passwordError = null) }

    fun onConfirmPasswordChange(value: String) =
        updateState { copy(confirmPassword = value, confirmPasswordError = null) }

    private fun updateState(update: RegisterUiState.() -> RegisterUiState) {
        _uiState.update(update)
    }

    fun validate(): Boolean {
        val current = _uiState.value

        val nameError = if (current.name.trim().length < 3) {
            "Ingresa un nombre válido"
        } else null

        val emailError = when {
            current.email.isBlank() -> "Ingresa tu correo"
            !ValidationUtils.isValidEmail(current.email) ->
                "Ingresa un correo válido"
            else -> null
        }

        val phoneError = when {
            current.phoneNumber.isBlank() -> "Ingresa tu teléfono"
            !ValidationUtils.isValidPhoneNumber(
                current.phoneCountryCode,
                current.phoneNumber
            ) -> "Ingresa un número válido para el país seleccionado"
            else -> null
        }

        val addressError = if (current.address.trim().length < 5) {
            "Ingresa una dirección válida"
        } else null

        val rutError = when {
            current.rut.isBlank() -> "Ingresa el RUT de empresa"
            !ValidationUtils.isValidChileanRut(current.rut) ->
                "Ingresa un RUT chileno válido"
            else -> null
        }

        val passwordError = when {
            current.password.isBlank() -> "Ingresa una contraseña"
            !ValidationUtils.isValidPassword(current.password) ->
                "Usa 8 caracteres, letras y números"
            else -> null
        }

        val confirmPasswordError = when {
            current.confirmPassword.isBlank() -> "Confirma tu contraseña"
            current.confirmPassword != current.password ->
                "Las contraseñas no coinciden"
            else -> null
        }

        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                phoneError = phoneError,
                addressError = addressError,
                rutError = rutError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
        }

        return listOf(
            nameError,
            emailError,
            phoneError,
            addressError,
            rutError,
            passwordError,
            confirmPasswordError
        ).all { it == null }
    }

    fun saveRegistration() {
        if (!validate()) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isSaving = true, saveError = null)
            }

            try {
                val current = _uiState.value

                userRepository.saveAccount(
                    UserAccount(
                        name = current.name.trim(),
                        email = current.email.trim(),
                        password = PasswordUtils.hashPassword(current.password),
                        phone = current.phone,
                        address = current.address.trim(),
                        rut = current.rut.trim()
                    )
                )

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        registrationSaved = true
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        saveError = "No se pudo guardar el registro"
                    )
                }
            }
        }
    }
}
