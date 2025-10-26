package com.pawcial.dto

import java.util.*

data class FacilityZoneDto(
    val id: UUID?,
    val facilityId: UUID?,
    val facilityName: String?,
    val name: String?,
    val purpose: String?,
    val isActive: Boolean
)

