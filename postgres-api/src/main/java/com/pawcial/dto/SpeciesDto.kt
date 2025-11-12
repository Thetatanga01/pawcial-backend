package com.pawcial.dto

import java.time.OffsetDateTime
import java.util.*

data class SpeciesDto(
    val id: UUID?,
    val scientificName: String?,
    val commonName: String?,
    val domesticStatus: String?,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)

