package com.pawcial.dto

import java.util.*

data class CreateBreedRequest(
    val speciesId: UUID,
    val name: String,
    val origin: String?
)

