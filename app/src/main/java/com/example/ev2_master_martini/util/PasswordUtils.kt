package com.example.ev2_master_martini.util

import java.security.MessageDigest

object PasswordUtils {

    /**
     * Calcula el hash SHA-256 de la contraseña y lo devuelve en hexadecimal.
     *
     * @param password Contraseña en texto plano (solo durante la sesión en memoria).
     * @return String con el hash hexadecimal de 64 caracteres.
     */
    fun hashPassword(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray(Charsets.UTF_8))

        return bytes.joinToString("") { byte ->
            "%02x".format(byte)
        }
    }

    /**
     * Compara una contraseña en texto plano contra un hash almacenado.
     *
     * @param rawPassword Contraseña ingresada por el usuario.
     * @param storedHash  Hash almacenado en la persistencia.
     * @return true si coinciden.
     */
    fun matches(rawPassword: String, storedHash: String): Boolean {
        return hashPassword(rawPassword) == storedHash
    }
}