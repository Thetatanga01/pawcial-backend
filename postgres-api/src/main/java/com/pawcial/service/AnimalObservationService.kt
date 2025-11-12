package com.pawcial.service

import com.pawcial.dto.CreateAnimalObservationRequest
import com.pawcial.entity.core.Animal
import com.pawcial.entity.core.AnimalObservation
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
class AnimalObservationService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    fun findAll(animalId: UUID?, all: Boolean = false): List<AnimalObservation> {
        val activeFilter = if (all) "" else " and isActive = true"
        return if (animalId != null) {
            AnimalObservation.find("animal.id = ?1$activeFilter", animalId).list()
        } else {
            if (all) {
                AnimalObservation.findAll().list()
            } else {
                AnimalObservation.find("isActive = true").list()
            }
        }
    }

    fun findById(id: UUID): AnimalObservation {
        return AnimalObservation.findById(id)
            ?: throw NotFoundException("AnimalObservation not found: $id")
    }

    @Transactional
    fun create(request: CreateAnimalObservationRequest): AnimalObservation {
        val animal = Animal.findById(request.animalId)
            ?: throw NotFoundException("Animal not found: ${request.animalId}")

        val person = Person.findById(request.personId)
            ?: throw NotFoundException("Person not found: ${request.personId}")

        val placement = request.placementId?.let {
            AnimalPlacement.findById(it) ?: throw NotFoundException("Placement not found: $it")
        }

        val observation = AnimalObservation().apply {
            this.animal = animal
            this.person = person
            this.placement = placement
            observationDate = request.observationDate
            category = request.category
            title = request.title
            description = request.description
            severity = request.severity
            attachmentPath = request.attachmentPath
            requiresVetAttention = request.requiresVetAttention
        }
        observation.persist()
        return observation
    }

    @Transactional
    fun delete(id: UUID) {
        val observation = AnimalObservation.findById(id)
            ?: throw NotFoundException("AnimalObservation not found: $id")
        observation.isActive = !observation.isActive
        observation.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        val observation = AnimalObservation.findById(id)
            ?: throw NotFoundException("AnimalObservation not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = observation.createdAt ?: throw IllegalStateException("AnimalObservation has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        observation.delete()
    }
}

