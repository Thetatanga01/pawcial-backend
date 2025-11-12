package com.pawcial.dto

import java.time.LocalDate
import java.util.*

data class CreateVolunteerRequest(
    val personId: UUID,
    val status: String = "ACTIVE",
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val volunteerCode: String?,
    val notes: String?,
    val areas: List<VolunteerAreaRequest>? = null
)

data class VolunteerAreaRequest(
    val areaCode: String,
    val proficiencyLevel: String?,
    val notes: String?
)

