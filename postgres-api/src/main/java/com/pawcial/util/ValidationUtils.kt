package com.pawcial.util

object ValidationUtils {

    fun validateCode(code: String, fieldName: String = "Code") {
        if (code.isBlank()) {
            throw IllegalArgumentException("$fieldName cannot be blank")
        }

        if (code.contains(" ")) {
            throw IllegalArgumentException("$fieldName cannot contain spaces. Use underscores or hyphens instead.")
        }

        if (code.contains("\t")) {
            throw IllegalArgumentException("$fieldName cannot contain tabs or whitespace characters")
        }
    }
}

