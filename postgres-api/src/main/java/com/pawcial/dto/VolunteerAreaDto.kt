package com.pawcial.dto

import java.time.OffsetDateTime
import java.util.*

data class VolunteerAreaDto(
    val volunteerId: UUID?,
    val areaCode: String?,
    val proficiencyLevel: String?,
    val notes: String?,
    val isActive: Boolean = true,
    val createdAt: OffsetDateTime?
)

