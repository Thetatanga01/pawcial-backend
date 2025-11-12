package com.pawcial.dto

data class UpdateSystemParameterRequest(
    val label: String?,
    val parameterValue: String?,
    val description: String?
)
