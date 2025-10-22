package com.pawcial.service

import com.pawcial.dto.CreateAnimalBreedCompositionRequest
import com.pawcial.entity.core.Animal
import com.pawcial.entity.core.AnimalBreedComposition
import com.pawcial.entity.core.Breed
import com.pawcial.entity.core.data.AnimalBreedCompositionId
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class AnimalBreedCompositionService {

    fun findAll(animalId: UUID?): List<AnimalBreedComposition> {
        return if (animalId != null) {
            AnimalBreedComposition.find("animal.id", animalId).list()
        } else {
            AnimalBreedComposition.findAll().list()
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
        val deleted = AnimalBreedComposition.deleteById(id)
        if (!deleted) {
            throw NotFoundException("AnimalBreedComposition not found for animal: $animalId and breed: $breedId")
        }
    }
}

