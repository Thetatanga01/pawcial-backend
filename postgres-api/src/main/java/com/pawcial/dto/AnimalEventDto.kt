package com.pawcial.dto

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

data class AnimalEventDto(
    val id: UUID?,
    val animalId: UUID?,
    val animalName: String?,
    val eventType: String?,
    val eventTypeLabel: String?,
    val eventAt: OffsetDateTime?,
    val facilityId: UUID?,
    val facilityName: String?,
    val unitId: UUID?,
    val unitCode: String?,
    val fromFacilityId: UUID?,
    val fromFacilityName: String?,
    val toFacilityId: UUID?,
    val toFacilityName: String?,
    val fromUnitId: UUID?,
    val fromUnitCode: String?,
    val toUnitId: UUID?,
    val toUnitCode: String?,
    val outcomeType: String?,
    val outcomeTypeLabel: String?,
    val sourceType: String?,
    val sourceTypeLabel: String?,
    val holdType: String?,
    val holdTypeLabel: String?,
    val personId: UUID?,
    val personName: String?,
    val volunteerId: UUID?,
    val volunteerName: String?,
    val medEventType: String?,
    val medEventTypeLabel: String?,
    val vaccineCode: String?,
    val vaccineLabel: String?,
    val medicationName: String?,
    val doseText: String?,
    val route: String?,
    val routeLabel: String?,
    val labTestName: String?,
    val resultText: String?,
    val nextDueDate: LocalDate?,
    val vetName: String?,
    val details: String?,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?,
    val isReadOnly: Boolean = false // true if event is older than 30 minutes
)

