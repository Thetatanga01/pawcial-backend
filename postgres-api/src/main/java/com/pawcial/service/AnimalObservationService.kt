package com.pawcial.service

import com.pawcial.dto.CreateAnimalObservationRequest
import com.pawcial.entity.core.Animal
import com.pawcial.entity.core.AnimalObservation
import com.pawcial.entity.core.AnimalPlacement
import com.pawcial.entity.core.Person
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class AnimalObservationService {

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
}

