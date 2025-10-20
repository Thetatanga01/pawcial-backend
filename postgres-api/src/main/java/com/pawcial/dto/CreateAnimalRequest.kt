package com.pawcial.dto

import java.util.*


data class CreateAnimalRequest(
    val name: String,
    val sex: String,
    val speciesId: UUID,
    val breedId: UUID?
)