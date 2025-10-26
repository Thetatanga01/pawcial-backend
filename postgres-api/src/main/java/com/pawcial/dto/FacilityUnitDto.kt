package com.pawcial.dto

import java.util.*

data class FacilityUnitDto(
    val id: UUID?,
    val facilityId: UUID?,
    val facilityName: String?,
    val zoneId: UUID?,
    val zoneName: String?,
    val code: String?,
    val type: String?,
    val capacity: Int?,
    val isActive: Boolean
)

