package com.pawcial.dto

import java.util.*

data class UpdateBreedRequest(
    val speciesId: UUID?,
    val name: String?,
    val origin: String?
)

