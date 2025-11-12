package com.pawcial.dto

data class CreateProficiencyLevelRequest(
    val code: String,
    val label: String,
    val description: String? = null,
    val displayOrder: Int? = null
)

data class UpdateProficiencyLevelRequest(
    val label: String? = null,
    val description: String? = null,
    val displayOrder: Int? = null
)

