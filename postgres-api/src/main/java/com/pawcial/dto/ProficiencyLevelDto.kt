package com.pawcial.dto

import java.time.OffsetDateTime

data class ProficiencyLevelDto(
    val code: String,
    val label: String,
    val description: String?,
    val displayOrder: Int?,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)

