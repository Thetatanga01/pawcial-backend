package com.pawcial.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class CreateAnimalPlacementRequest(
    val animalId: UUID,
    val personId: UUID,
    val placementType: String,
    val status: String = "ACTIVE",
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val expectedEndDate: LocalDate?,
    val placementFee: BigDecimal?,
    val notes: String?,
    val intakeEventId: UUID?,
    val outcomeEventId: UUID?
)

