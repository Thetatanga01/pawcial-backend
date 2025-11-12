package com.pawcial.dto

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

data class UpdateAnimalRequest(
    val speciesId: UUID?,
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
    val isMixed: Boolean?,
    val originNote: String?,
    val currentUnitId: UUID?,
    val currentSince: OffsetDateTime?,
    val temperamentCodes: List<String>?,
    val healthFlagCodes: List<String>?
)