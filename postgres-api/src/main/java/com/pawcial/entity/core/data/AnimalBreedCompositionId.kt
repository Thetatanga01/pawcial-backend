package com.pawcial.entity.core.data

import java.io.Serializable
import java.util.UUID

data class AnimalBreedCompositionId(
    var animal: UUID? = null,
    var breed: UUID? = null
) : Serializable