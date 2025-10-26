package com.pawcial.service

import com.pawcial.dto.CreateAnimalEventRequest
import com.pawcial.entity.core.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class AnimalEventService {

    fun findAll(animalId: UUID?, all: Boolean = false): List<AnimalEvent> {
        val activeFilter = if (all) "" else " and isActive = true"
        return if (animalId != null) {
            AnimalEvent.find("animal.id = ?1$activeFilter", animalId).list()
        } else {
            if (all) {
                AnimalEvent.findAll().list()
            } else {
                AnimalEvent.find("isActive = true").list()
            }
        }
    }

    fun findById(id: UUID): AnimalEvent {
        return AnimalEvent.findById(id)
            ?: throw NotFoundException("AnimalEvent not found: $id")
    }

    @Transactional
    fun create(request: CreateAnimalEventRequest): AnimalEvent {
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
        return event
    }

    @Transactional
    fun delete(id: UUID) {
        val event = AnimalEvent.findById(id)
            ?: throw NotFoundException("AnimalEvent not found: $id")
        event.isActive = !event.isActive
        event.persist()
    }
}

