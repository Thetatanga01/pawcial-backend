package com.pawcial.dto

import java.time.LocalDate
import java.util.*

data class CreateAnimalObservationRequest(
    val animalId: UUID,
    val placementId: UUID?,
    val personId: UUID,
    val observationDate: LocalDate = LocalDate.now(),
    val category: String,
    val title: String?,
    val description: String,
    val severity: String?,
    val attachmentPath: String?,
    val requiresVetAttention: Boolean = false
)

