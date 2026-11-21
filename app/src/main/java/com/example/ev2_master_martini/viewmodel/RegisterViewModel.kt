package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.domain.UserValidator
import com.example.ev2_master_martini.model.RegisterUiState
import com.example.ev2_master_martini.model.UserAccount
import com.example.ev2_master_martini.util.PasswordUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * 1. Recibe el repositorio por constructor (inyección de dependencias).
 * 2. Usa [UserValidator] para las validaciones, evitando duplicar la lógica
 *    que ya existe en ProfileViewModel.
 */
class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

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

        val nameError = UserValidator.nameError(current.name)
        val emailError = UserValidator.emailError(current.email)
        val phoneError = UserValidator.phoneError(
            current.phoneCountryCode,
            current.phoneNumber
        )
        val addressError = UserValidator.addressError(current.address)
        val rutError = UserValidator.rutError(current.rut)
        val passwordError = UserValidator.passwordError(current.password)
        val confirmPasswordError = UserValidator.confirmPasswordError(
            current.password,
            current.confirmPassword
        )

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
