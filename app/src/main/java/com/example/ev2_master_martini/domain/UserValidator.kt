package com.example.ev2_master_martini.domain

import com.example.ev2_master_martini.util.ValidationUtils

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Centraliza las validaciones de los datos de cuenta que antes estaban
 * duplicadas entre RegisterViewModel y ProfileViewModel. Cada función devuelve
 * el mensaje de error o null si el valor es válido, de modo que la UI solo
 * muestre el texto.
 */
object UserValidator {

    fun nameError(name: String): String? =
        if (name.trim().length < 3) "Ingresa un nombre válido" else null

    fun emailError(email: String): String? = when {
        email.isBlank() -> "Ingresa tu correo"
        !ValidationUtils.isValidEmail(email) -> "Ingresa un correo válido"
        else -> null
    }

    /** Validación para el registro, donde el usuario elige el país del teléfono. */
    fun phoneError(countryCode: String, number: String): String? = when {
        number.isBlank() -> "Ingresa tu teléfono"
        !ValidationUtils.isValidPhoneNumber(countryCode, number) ->
            "Ingresa un número válido para el país seleccionado"
        else -> null
    }

    /** Validación para el perfil, donde se exige teléfono chileno. */
    fun chileanPhoneError(phone: String): String? = when {
        phone.isBlank() -> "Ingresa tu teléfono"
        !ValidationUtils.isValidChileanPhone(phone) -> "Ingresa un número válido"
        else -> null
    }

    fun addressError(address: String): String? =
        if (address.trim().length < 5) "Ingresa una dirección válida" else null

    fun rutError(rut: String): String? = when {
        rut.isBlank() -> "Ingresa el RUT de empresa"
        !ValidationUtils.isValidChileanRut(rut) -> "Ingresa un RUT chileno válido"
        else -> null
    }

    fun passwordError(password: String): String? = when {
        password.isBlank() -> "Ingresa una contraseña"
        !ValidationUtils.isValidPassword(password) -> "Usa 8 caracteres, letras y números"
        else -> null
    }

    fun confirmPasswordError(password: String, confirmPassword: String): String? = when {
        confirmPassword.isBlank() -> "Confirma tu contraseña"
        confirmPassword != password -> "Las contraseñas no coinciden"
        else -> null
    }
}
