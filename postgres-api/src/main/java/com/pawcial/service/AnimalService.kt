package com.pawcial.service
import com.pawcial.dto.*
import com.pawcial.entity.core.Animal
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class AnimalService {

    fun findAll(speciesId: UUID?, status: String?): List<AnimalDto> {
        var query = "1=1"
        val params = mutableMapOf<String, Any>()

        if (speciesId != null) {
            query += " and species.id = :speciesId"
            params["speciesId"] = speciesId
        }

        if (status != null) {
            query += " and status = :status"
            params["status"] = status
        }

        return Animal.find(query, params)
            .list()
            .map { it.toDto() }
    }

    fun findById(id: UUID): AnimalDto {
        return Animal.findById(id)?.toDto()
            ?: throw NotFoundException("Animal not found: $id")
    }

    @Transactional
    fun create(request: CreateAnimalRequest): AnimalDto {
        val species = com.pawcial.entity.core.Species.findById(request.speciesId)
            ?: throw NotFoundException("Species not found: ${request.speciesId}")

        val breed = request.breedId?.let {
            com.pawcial.entity.core.Breed.findById(it)
                ?: throw NotFoundException("Breed not found: $it")
        }

        val animal = Animal().apply {
            this.species = species
            this.breed = breed
            name = request.name
            sex = request.sex
            birthDate = request.birthDate
            ageMonthsEst = request.ageMonthsEst
            size = request.size
            color = request.color
            trainingLevel = request.trainingLevel
            sterilized = request.sterilized
            isMixed = request.isMixed
            originNote = request.originNote
            currentUnitId = request.currentUnitId
            currentSince = request.currentSince
        }
        animal.persist()
        return animal.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdateAnimalRequest): AnimalDto {
        val animal = Animal.findById(id)
            ?: throw NotFoundException("Animal not found: $id")

        request.speciesId?.let {
            animal.species = com.pawcial.entity.core.Species.findById(it)
                ?: throw NotFoundException("Species not found: $it")
        }

        request.breedId?.let {
            animal.breed = com.pawcial.entity.core.Breed.findById(it)
                ?: throw NotFoundException("Breed not found: $it")
        }

        animal.apply {
            request.name?.let { name = it }
            request.sex?.let { sex = it }
            request.birthDate?.let { birthDate = it }
            request.ageMonthsEst?.let { ageMonthsEst = it }
            request.size?.let { size = it }
            request.color?.let { color = it }
            request.trainingLevel?.let { trainingLevel = it }
            request.sterilized?.let { sterilized = it }
            request.isMixed?.let { isMixed = it }
            request.originNote?.let { originNote = it }
            request.currentUnitId?.let { currentUnitId = it }
            request.currentSince?.let { currentSince = it }
        }
        animal.persist()
        return animal.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val deleted = Animal.deleteById(id)
        if (!deleted) {
            throw NotFoundException("Animal not found: $id")
        }
    }
}
