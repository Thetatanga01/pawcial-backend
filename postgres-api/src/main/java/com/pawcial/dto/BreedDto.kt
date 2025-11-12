package com.pawcial.dto

import java.time.OffsetDateTime
import java.util.*

data class BreedDto(
    val id: UUID?,
    val speciesId: UUID?,
    val speciesName: String?,
    val name: String?,
    val origin: String?,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)
