package com.pawcial.dto

import java.util.*

data class UpdateFacilityZoneRequest(
    val facilityId: UUID?,
    val name: String?,
    val purpose: String?
)

