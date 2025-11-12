package com.pawcial.service

import com.pawcial.dto.AnimalEventDto
import com.pawcial.dto.CreateAnimalEventRequest
import com.pawcial.dto.UpdateAnimalEventRequest
import com.pawcial.entity.core.*
import com.pawcial.entity.dictionary.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.BadRequestException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class AnimalEventService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    fun findAll(animalId: UUID?, all: Boolean = false): List<AnimalEventDto> {
        val activeFilter = if (all) "" else " and isActive = true"
        val orderBy = " order by eventAt desc"

        val events = if (animalId != null) {
            AnimalEvent.find("animal.id = ?1$activeFilter$orderBy", animalId).list()
        } else {
            if (all) {
                AnimalEvent.find("1=1$orderBy").list()
            } else {
                AnimalEvent.find("isActive = true$orderBy").list()
            }
        }
        return events.map { toDto(it) }
    }

    fun findById(id: UUID): AnimalEventDto {
        val event = AnimalEvent.findById(id)
            ?: throw NotFoundException("AnimalEvent not found: $id")
        return toDto(event)
    }

    @Transactional
    fun create(request: CreateAnimalEventRequest): AnimalEventDto {
        val animal = Animal.findById(request.animalId)
            ?: throw NotFoundException("Animal not found: ${request.animalId}")

        val facility = request.facilityId?.let {
            Facility.findById(it) ?: throw NotFoundException("Facility not found: $it")
        }

        val unit = request.unitId?.let {
            FacilityUnit.findById(it) ?: throw NotFoundException("FacilityUnit not found: $it")
        }

        val fromFacility = request.fromFacilityId?.let {
            Facility.findById(it) ?: throw NotFoundException("FromFacility not found: $it")
        }

        val toFacility = request.toFacilityId?.let {
            Facility.findById(it) ?: throw NotFoundException("ToFacility not found: $it")
        }

        val fromUnit = request.fromUnitId?.let {
            FacilityUnit.findById(it) ?: throw NotFoundException("FromUnit not found: $it")
        }

        val toUnit = request.toUnitId?.let {
            FacilityUnit.findById(it) ?: throw NotFoundException("ToUnit not found: $it")
        }

        val person = request.personId?.let {
            Person.findById(it) ?: throw NotFoundException("Person not found: $it")
        }

        val volunteer = request.volunteerId?.let {
            Volunteer.findById(it) ?: throw NotFoundException("Volunteer not found: $it")
        }

        val event = AnimalEvent().apply {
            this.animal = animal
            eventType = request.eventType
            eventAt = request.eventAt
            this.facility = facility
            this.unit = unit
            this.fromFacility = fromFacility
            this.toFacility = toFacility
            this.fromUnit = fromUnit
            this.toUnit = toUnit
            outcomeType = request.outcomeType
            sourceType = request.sourceType
            holdType = request.holdType
            this.person = person
            this.volunteer = volunteer
            medEventType = request.medEventType
            vaccineCode = request.vaccineCode
            medicationName = request.medicationName
            doseText = request.doseText
            route = request.route
            labTestName = request.labTestName
            resultText = request.resultText
            nextDueDate = request.nextDueDate
            vetName = request.vetName
            details = request.details
        }
        event.persist()
        return toDto(event)
    }

    @Transactional
    fun delete(id: UUID) {
        val event = AnimalEvent.findById(id)
            ?: throw NotFoundException("AnimalEvent not found: $id")
        event.isActive = !event.isActive
        event.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        val event = AnimalEvent.findById(id)
            ?: throw NotFoundException("AnimalEvent not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = event.createdAt ?: throw IllegalStateException("AnimalEvent has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        event.delete()
    }

    /**
     * Checks if the given timestamp is older than 30 minutes from now
     */
    private fun isOlderThan30Minutes(createdAt: OffsetDateTime?): Boolean {
        if (createdAt == null) return false
        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds
        return elapsedSeconds > hardDeleteWindowSeconds
    }

    @Transactional
    fun update(id: UUID, request: UpdateAnimalEventRequest): AnimalEventDto {
        val event = AnimalEvent.findById(id)
            ?: throw NotFoundException("AnimalEvent not found: $id")

        // 30 dakikadan eski event'ler güncellenemez
        if (isOlderThan30Minutes(event.createdAt)) {
            throw BadRequestException("Cannot update event: Event is older than 30 minutes and is now read-only")
        }

        request.eventType?.let { event.eventType = it }
        request.eventAt?.let { event.eventAt = it }

        // ...existing code...

        request.facilityId?.let {
            event.facility = Facility.findById(it)
                ?: throw NotFoundException("Facility not found: $it")
        }

        request.unitId?.let {
            event.unit = FacilityUnit.findById(it)
                ?: throw NotFoundException("FacilityUnit not found: $it")
        }

        request.fromFacilityId?.let {
            event.fromFacility = Facility.findById(it)
                ?: throw NotFoundException("FromFacility not found: $it")
        }

        request.toFacilityId?.let {
            event.toFacility = Facility.findById(it)
                ?: throw NotFoundException("ToFacility not found: $it")
        }

        request.fromUnitId?.let {
            event.fromUnit = FacilityUnit.findById(it)
                ?: throw NotFoundException("FromUnit not found: $it")
        }

        request.toUnitId?.let {
            event.toUnit = FacilityUnit.findById(it)
                ?: throw NotFoundException("ToUnit not found: $it")
        }

        request.personId?.let {
            event.person = Person.findById(it)
                ?: throw NotFoundException("Person not found: $it")
        }

        request.volunteerId?.let {
            event.volunteer = Volunteer.findById(it)
                ?: throw NotFoundException("Volunteer not found: $it")
        }

        request.outcomeType?.let { event.outcomeType = it }
        request.sourceType?.let { event.sourceType = it }
        request.holdType?.let { event.holdType = it }
        request.medEventType?.let { event.medEventType = it }
        request.vaccineCode?.let { event.vaccineCode = it }
        request.medicationName?.let { event.medicationName = it }
        request.doseText?.let { event.doseText = it }
        request.route?.let { event.route = it }
        request.labTestName?.let { event.labTestName = it }
        request.resultText?.let { event.resultText = it }
        request.nextDueDate?.let { event.nextDueDate = it }
        request.vetName?.let { event.vetName = it }
        request.details?.let { event.details = it }

        event.persist()
        return toDto(event)
    }

    private fun toDto(event: AnimalEvent): AnimalEventDto {
        return AnimalEventDto(
            id = event.id,
            animalId = event.animal?.id,
            animalName = event.animal?.name,
            eventType = event.eventType,
            eventTypeLabel = event.eventType?.let { EventType.findById(it)?.label },
            eventAt = event.eventAt,
            facilityId = event.facility?.id,
            facilityName = event.facility?.name,
            unitId = event.unit?.id,
            unitCode = event.unit?.code,
            fromFacilityId = event.fromFacility?.id,
            fromFacilityName = event.fromFacility?.name,
            toFacilityId = event.toFacility?.id,
            toFacilityName = event.toFacility?.name,
            fromUnitId = event.fromUnit?.id,
            fromUnitCode = event.fromUnit?.code,
            toUnitId = event.toUnit?.id,
            toUnitCode = event.toUnit?.code,
            outcomeType = event.outcomeType,
            outcomeTypeLabel = event.outcomeType?.let { OutcomeType.findById(it)?.label },
            sourceType = event.sourceType,
            sourceTypeLabel = event.sourceType?.let { SourceType.findById(it)?.label },
            holdType = event.holdType,
            holdTypeLabel = event.holdType?.let { HoldType.findById(it)?.label },
            personId = event.person?.id,
            personName = event.person?.fullName,
            volunteerId = event.volunteer?.id,
            volunteerName = event.volunteer?.person?.fullName,
            medEventType = event.medEventType,
            medEventTypeLabel = event.medEventType?.let { MedEventType.findById(it)?.label },
            vaccineCode = event.vaccineCode,
            vaccineLabel = event.vaccineCode?.let { Vaccine.findById(it)?.label },
            medicationName = event.medicationName,
            doseText = event.doseText,
            route = event.route,
            routeLabel = event.route?.let { DoseRoute.findById(it)?.label },
            labTestName = event.labTestName,
            resultText = event.resultText,
            nextDueDate = event.nextDueDate,
            vetName = event.vetName,
            details = event.details,
            isActive = event.isActive,
            createdAt = event.createdAt,
            updatedAt = event.updatedAt,
            isReadOnly = isOlderThan30Minutes(event.createdAt)
        )
    }
}

