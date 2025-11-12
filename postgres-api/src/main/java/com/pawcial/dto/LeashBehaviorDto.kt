package com.pawcial.dto

import java.time.OffsetDateTime

data class LeashBehaviorDto(
    val code: String,
    val label: String,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)

