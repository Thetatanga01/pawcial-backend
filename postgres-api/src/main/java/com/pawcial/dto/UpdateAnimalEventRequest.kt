package com.pawcial.dto

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

data class UpdateAnimalEventRequest(
    val eventType: String?,
    val eventAt: OffsetDateTime?,
    val facilityId: UUID?,
    val unitId: UUID?,
    val fromFacilityId: UUID?,
    val toFacilityId: UUID?,
    val fromUnitId: UUID?,
    val toUnitId: UUID?,
    val outcomeType: String?,
    val sourceType: String?,
    val holdType: String?,
    val personId: UUID?,
    val volunteerId: UUID?,
    val medEventType: String?,
    val vaccineCode: String?,
    val medicationName: String?,
    val doseText: String?,
    val route: String?,
    val labTestName: String?,
    val resultText: String?,
    val nextDueDate: LocalDate?,
    val vetName: String?,
    val details: String?
)

