package com.pawcial.dto

import java.util.*

data class CreateFacilityUnitRequest(
    val facilityId: UUID,
    val zoneId: UUID?,
    val code: String,
    val type: String,
    val capacity: Int?
)

