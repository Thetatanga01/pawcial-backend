package com.pawcial.dto

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

data class VolunteerDto(
    val id: UUID?,
    val personId: UUID?,
    val personName: String?,
    val status: String,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val volunteerCode: String?,
    val notes: String?,
    val areas: List<VolunteerAreaDto>? = null,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)

