package com.pawcial.dto

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

data class AnimalDto(
    val id: UUID?,
    val speciesId: UUID?,
    val speciesName: String?,
    val breedId: UUID?,
    val breedName: String?,
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
    val temperaments: List<String>?,
    val healthFlags: List<String>?,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)


