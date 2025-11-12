package com.pawcial.util

object ValidationUtils {

    /**
     * Validates code field according to strict rules:
     * 1. Only English alphabet characters (a-z, A-Z)
     * 2. Numbers (0-9)
     * 3. Underscore (_) for spacing
     * 4. No spaces, no special characters, no non-English characters
     */
    fun validateCode(code: String, fieldName: String = "Code") {
        if (code.isBlank()) {
            throw IllegalArgumentException("$fieldName cannot be blank")
        }

        // Check for spaces
        if (code.contains(" ")) {
            throw IllegalArgumentException("$fieldName cannot contain spaces. Use underscores (_) instead.")
        }

        // Check for tabs or other whitespace
        if (code.contains("\t") || code.any { it.isWhitespace() }) {
            throw IllegalArgumentException("$fieldName cannot contain whitespace characters. Use underscores (_) instead.")
        }

        // Regex: Only a-z, A-Z, 0-9, and underscore allowed
        val validPattern = Regex("^[a-zA-Z0-9_]+$")
        if (!code.matches(validPattern)) {
            throw IllegalArgumentException(
                "$fieldName can only contain English letters (a-z, A-Z), numbers (0-9), and underscores (_). " +
                "No spaces, special characters, or non-English characters are allowed."
            )
        }
    }
}

