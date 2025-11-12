package com.pawcial.dto

import java.time.OffsetDateTime
import java.util.*

data class FacilityDto(
    val id: UUID?,
    val name: String?,
    val type: String?,
    val country: String?,
    val city: String?,
    val address: String?,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)

