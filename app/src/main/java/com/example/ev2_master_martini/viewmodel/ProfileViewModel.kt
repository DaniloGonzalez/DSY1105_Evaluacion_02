package com.example.ev2_master_martini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.model.ProfileUiState
import com.example.ev2_master_martini.model.UserAccount
import com.example.ev2_master_martini.model.UserInterests
import com.example.ev2_master_martini.util.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            userRepository.logout()
            onComplete()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val account = userRepository.getAccount().first()
            val interests = userRepository.getInterests().first()
            val largeText = userRepository.isLargeText().first()

            _uiState.value = ProfileUiState(
                isLoading = false,
                name = account?.name.orEmpty(),
                email = account?.email.orEmpty(),
                phone = account?.phone.orEmpty(),
                address = account?.address.orEmpty(),
                rut = account?.rut.orEmpty(),
                businessType = interests?.businessType.orEmpty(),
                businessSize = interests?.businessSize.orEmpty(),
                catalogOptions = interests?.catalogOptions.orEmpty(),
                largeTextEnabled = largeText
            )
        }
    }

    // ---- Edición de datos de contacto ----

    fun onNameChange(value: String) =
        updateState { copy(name = value, nameError = null) }

    fun onEmailChange(value: String) =
        updateState { copy(email = value, emailError = null) }

    fun onPhoneChange(value: String) =
        updateState { copy(phone = value, phoneError = null) }

    fun onAddressChange(value: String) =
        updateState { copy(address = value, addressError = null) }

    fun onRutChange(value: String) =
        updateState { copy(rut = value, rutError = null) }

    // ---- Edición de intereses ----

    fun onBusinessTypeChange(value: String) =
        updateState { copy(businessType = value) }

    fun onBusinessSizeChange(value: String) =
        updateState { copy(businessSize = value) }

    fun onCatalogOptionToggle(option: String) {
        // Se usa _uiState.update directamente (lambda con parámetro), porque
        // updateState usa una lambda con receiver (sin parámetros).
        _uiState.update { current ->
            val updated = current.catalogOptions.toMutableSet()
            if (!updated.add(option)) {
                updated.remove(option)
            }
            current.copy(catalogOptions = updated)
        }
    }

    // ---- Accesibilidad ----

    /** Persiste el estado del modo de texto grande. */
    fun onLargeTextChange(enabled: Boolean) {
        updateState { copy(largeTextEnabled = enabled) }
        viewModelScope.launch {
            userRepository.setLargeText(enabled)
        }
    }

    private fun updateState(update: ProfileUiState.() -> ProfileUiState) {
        _uiState.update(update)
    }

    private fun validateAccount(): Boolean {
        val current = _uiState.value

        val nameError = if (current.name.trim().length < 3) {
            "Ingresa un nombre válido"
        } else null

        val emailError = when {
            current.email.isBlank() -> "Ingresa tu correo"
            !ValidationUtils.isValidEmail(current.email) -> "Ingresa un correo válido"
            else -> null
        }

        val phoneError = when {
            current.phone.isBlank() -> "Ingresa tu teléfono"
            !ValidationUtils.isValidChileanPhone(current.phone) ->
                "Ingresa un número válido"
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

        _uiState.value = current.copy(
            nameError = nameError,
            emailError = emailError,
            phoneError = phoneError,
            addressError = addressError,
            rutError = rutError
        )

        return listOf(nameError, emailError, phoneError, addressError, rutError)
            .all { it == null }
    }

    /** Guarda los datos de contacto editados en la persistencia local. */
    fun saveAccount(onComplete: () -> Unit) {
        if (!validateAccount()) return

        viewModelScope.launch {
            val current = _uiState.value
            userRepository.updateAccount(
                UserAccount(
                    name = current.name.trim(),
                    email = current.email.trim(),
                    // El hash se conserva: al editar el perfil NO se modifica la
                    // contraseña. Se usa el valor ya almacenado.
                    password = userRepository.getAccount().first()?.password.orEmpty(),
                    phone = current.phone.trim(),
                    address = current.address.trim(),
                    rut = current.rut.trim()
                )
            )
            _uiState.value = current.copy(isSaving = false, saveMessage = "Datos guardados")
            onComplete()
        }
    }

    /** Guarda los intereses editados en la persistencia local. */
    fun saveInterests() {
        viewModelScope.launch {
            val current = _uiState.value
            userRepository.updateInterests(
                UserInterests(
                    businessType = current.businessType,
                    businessSize = current.businessSize,
                    catalogOptions = current.catalogOptions
                )
            )
            _uiState.value = current.copy(
                isSaving = false,
                saveMessage = "Intereses actualizados"
            )
        }
    }
}