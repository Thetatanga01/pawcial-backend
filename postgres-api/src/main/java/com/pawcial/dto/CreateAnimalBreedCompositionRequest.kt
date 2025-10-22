package com.pawcial.dto

import java.math.BigDecimal
import java.util.*

data class CreateAnimalBreedCompositionRequest(
    val animalId: UUID,
    val breedId: UUID,
    val percentage: BigDecimal?,
    val notes: String?
)

