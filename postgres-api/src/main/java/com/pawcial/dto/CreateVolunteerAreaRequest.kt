package com.pawcial.dto

import java.util.*

data class CreateVolunteerAreaRequest(
    val volunteerId: UUID,
    val areaCode: String,
    val proficiencyLevel: String?,
    val notes: String?
)

