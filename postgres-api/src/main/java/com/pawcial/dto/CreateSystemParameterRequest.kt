package com.pawcial.dto

data class CreateSystemParameterRequest(
    val code: String,
    val label: String,
    val parameterValue: String,
    val description: String?
)

