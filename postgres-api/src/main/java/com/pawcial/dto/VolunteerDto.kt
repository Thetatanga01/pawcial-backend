package com.pawcial.dto

import java.time.LocalDate
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
    val isActive: Boolean
)

