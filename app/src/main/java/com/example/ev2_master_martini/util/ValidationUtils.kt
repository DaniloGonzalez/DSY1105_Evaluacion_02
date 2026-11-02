package com.example.ev2_master_martini.util

object ValidationUtils {

    private val EMAIL_REGEX = Regex(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )

    fun isValidEmail(value: String): Boolean {
        return EMAIL_REGEX.matches(value.trim())
    }

    fun isValidPhoneNumber(countryCode: String, number: String): Boolean {
        val digits = number.filter { it.isDigit() }

        return if (countryCode == "+56") {
            digits.matches(Regex("^9\\d{8}$"))
        } else {
            digits.length in 7..15
        }
    }

    fun isValidChileanPhone(value: String): Boolean {
        val normalized = value
            .replace(" ", "")
            .replace("-", "")

        return normalized.matches(
            Regex("^(?:\\+?56)?9\\d{8}$")
        )
    }

    fun isValidChileanRut(value: String): Boolean {
        val cleanRut = value
            .replace(".", "")
            .replace("-", "")
            .uppercase()

        if (cleanRut.length < 2) return false

        val body = cleanRut.dropLast(1)
        val verifier = cleanRut.last()

        if (!body.all { it.isDigit() }) return false

        var multiplier = 2
        var sum = 0

        for (digit in body.reversed()) {
            sum += digit.digitToInt() * multiplier
            multiplier = if (multiplier == 7) 2 else multiplier + 1
        }

        val remainder = 11 - (sum % 11)
        val expectedVerifier = when (remainder) {
            11 -> '0'
            10 -> 'K'
            else -> remainder.digitToChar()
        }

        return verifier == expectedVerifier
    }

    fun isValidPassword(value: String): Boolean {
        return value.length >= 8 &&
            value.any { it.isDigit() } &&
            value.any { it.isLetter() }
    }
}
