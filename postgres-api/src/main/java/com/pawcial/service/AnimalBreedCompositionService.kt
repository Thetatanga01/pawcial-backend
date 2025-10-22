package com.pawcial.service

import com.pawcial.dto.CreateAnimalBreedCompositionRequest
import com.pawcial.entity.core.Animal
import com.pawcial.entity.core.AnimalBreedComposition
import com.pawcial.entity.core.Breed
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class AnimalBreedCompositionService {

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
}

