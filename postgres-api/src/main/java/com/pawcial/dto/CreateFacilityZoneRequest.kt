package com.pawcial.dto

import java.util.*

data class CreateFacilityZoneRequest(
    val facilityId: UUID,
    val name: String,
    val purpose: String?
)

