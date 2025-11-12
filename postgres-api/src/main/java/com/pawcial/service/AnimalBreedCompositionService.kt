package com.pawcial.service

import com.pawcial.dto.CreateAnimalBreedCompositionRequest
import com.pawcial.entity.core.Animal
import com.pawcial.entity.core.AnimalBreedComposition
import com.pawcial.entity.core.Breed
import com.pawcial.entity.core.data.AnimalBreedCompositionId
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class AnimalBreedCompositionService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    fun findAll(animalId: UUID?, all: Boolean = false): List<AnimalBreedComposition> {
        val activeFilter = if (all) "" else " and isActive = true"
        return if (animalId != null) {
            AnimalBreedComposition.find("animal.id = ?1$activeFilter", animalId).list()
        } else {
            if (all) {
                AnimalBreedComposition.findAll().list()
            } else {
                AnimalBreedComposition.find("isActive = true").list()
            }
        }
    }

    fun findById(animalId: UUID, breedId: UUID): AnimalBreedComposition {
        return AnimalBreedComposition.findById(AnimalBreedCompositionId(animalId, breedId))
            ?: throw NotFoundException("AnimalBreedComposition not found for animal: $animalId and breed: $breedId")
    }

    @Transactional
    fun create(request: CreateAnimalBreedCompositionRequest): AnimalBreedComposition {
        val animal = Animal.findById(request.animalId)
            ?: throw NotFoundException("Animal not found: ${request.animalId}")

        val breed = Breed.findById(request.breedId)
            ?: throw NotFoundException("Breed not found: ${request.breedId}")

        val composition = AnimalBreedComposition().apply {
            this.animal = animal
            this.breed = breed
            percentage = request.percentage
            notes = request.notes
        }
        composition.persist()
        return composition
    }

    @Transactional
    fun delete(animalId: UUID, breedId: UUID) {
        val id = AnimalBreedCompositionId(animalId, breedId)
        val composition = AnimalBreedComposition.findById(id)
            ?: throw NotFoundException("AnimalBreedComposition not found for animal: $animalId and breed: $breedId")
        composition.isActive = !composition.isActive
        composition.persist()
    }

    @Transactional
    fun hardDelete(animalId: UUID, breedId: UUID) {
        val id = AnimalBreedCompositionId(animalId, breedId)
        val composition = AnimalBreedComposition.findById(id)
            ?: throw NotFoundException("AnimalBreedComposition not found for animal: $animalId and breed: $breedId")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = composition.createdAt ?: throw IllegalStateException("AnimalBreedComposition has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        composition.delete()
    }
}

