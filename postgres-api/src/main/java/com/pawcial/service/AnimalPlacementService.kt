package com.pawcial.service

import com.pawcial.dto.CreateAnimalPlacementRequest
import com.pawcial.entity.core.Animal
import com.pawcial.entity.core.AnimalEvent
import com.pawcial.entity.core.AnimalPlacement
import com.pawcial.entity.core.Person
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class AnimalPlacementService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    fun findAll(animalId: UUID?, personId: UUID?, all: Boolean = false): List<AnimalPlacement> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (animalId != null) {
            query += " and animal.id = :animalId"
            params["animalId"] = animalId
        }

        if (personId != null) {
            query += " and person.id = :personId"
            params["personId"] = personId
        }

        return AnimalPlacement.find(query, params).list()
    }

    fun findById(id: UUID): AnimalPlacement {
        return AnimalPlacement.findById(id)
            ?: throw NotFoundException("AnimalPlacement not found: $id")
    }

    @Transactional
    fun create(request: CreateAnimalPlacementRequest): AnimalPlacement {
        val animal = Animal.findById(request.animalId)
            ?: throw NotFoundException("Animal not found: ${request.animalId}")

        val person = Person.findById(request.personId)
            ?: throw NotFoundException("Person not found: ${request.personId}")

        val intakeEvent = request.intakeEventId?.let {
            AnimalEvent.findById(it) ?: throw NotFoundException("IntakeEvent not found: $it")
        }

        val outcomeEvent = request.outcomeEventId?.let {
            AnimalEvent.findById(it) ?: throw NotFoundException("OutcomeEvent not found: $it")
        }

        val placement = AnimalPlacement().apply {
            this.animal = animal
            this.person = person
            placementType = request.placementType
            status = request.status
            startDate = request.startDate
            endDate = request.endDate
            expectedEndDate = request.expectedEndDate
            placementFee = request.placementFee
            notes = request.notes
            this.intakeEvent = intakeEvent
            this.outcomeEvent = outcomeEvent
        }
        placement.persist()
        return placement
    }

    @Transactional
    fun delete(id: UUID) {
        val placement = AnimalPlacement.findById(id)
            ?: throw NotFoundException("AnimalPlacement not found: $id")
        placement.isActive = !placement.isActive
        placement.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        val placement = AnimalPlacement.findById(id)
            ?: throw NotFoundException("AnimalPlacement not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = placement.createdAt ?: throw IllegalStateException("AnimalPlacement has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        placement.delete()
    }
}

