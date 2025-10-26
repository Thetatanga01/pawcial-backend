package com.pawcial.service

import com.pawcial.dto.BreedDto
import com.pawcial.dto.CreateBreedRequest
import com.pawcial.dto.UpdateBreedRequest
import com.pawcial.entity.core.Breed
import com.pawcial.entity.core.Species
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class BreedService {

    fun findAll(): List<BreedDto> {
        return Breed.findAll().list()
            .map { it.toDto() }
    }

    fun findById(id: UUID): BreedDto {
        return Breed.findById(id)?.toDto()
            ?: throw NotFoundException("Breed not found: $id")
    }

    @Transactional
    fun create(request: CreateBreedRequest): BreedDto {
        val species = Species.findById(request.speciesId)
            ?: throw NotFoundException("Species not found: ${request.speciesId}")

        val breed = Breed().apply {
            this.species = species
            name = request.name
            origin = request.origin
        }
        breed.persist()
        return breed.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdateBreedRequest): BreedDto {
        val breed = Breed.findById(id)
            ?: throw NotFoundException("Breed not found: $id")

        request.speciesId?.let {
            breed.species = Species.findById(it)
                ?: throw NotFoundException("Species not found: $it")
        }

        breed.apply {
            request.name?.let { name = it }
            request.origin?.let { origin = it }
        }
        breed.persist()
        return breed.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val deleted = Breed.deleteById(id)
        if (!deleted) {
            throw NotFoundException("Breed not found: $id")
        }
    }
}
