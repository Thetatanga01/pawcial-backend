package com.pawcial.dto

import java.time.LocalDate
import java.util.*

data class UpdateVolunteerRequest(
    val personId: UUID?,
    val status: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val volunteerCode: String?,
    val notes: String?,
    val areas: List<VolunteerAreaRequest>?
)
