package com.pawcial.dto

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

data class CreateAnimalRequest(
    val speciesId: UUID,
    val breedId: UUID?,
    val name: String?,
    val sex: String?,
    val birthDate: LocalDate?,
    val ageMonthsEst: Int?,
    val size: String?,
    val color: String?,
    val leashBehavior: String?,
    val trainingLevel: String?,
    val sterilized: Boolean?,
    val isMixed: Boolean = false,
    val originNote: String?,
    val currentUnitId: UUID?,
    val currentSince: OffsetDateTime?,
    val temperamentCodes: List<String>? = null,
    val healthFlagCodes: List<String>? = null
)