package com.pawcial.dto

import java.util.*

data class BreedDto(
    val id: UUID?,
    val speciesId: UUID?,
    val speciesName: String?,
    val name: String?,
    val origin: String?,
    val isActive: Boolean
)
