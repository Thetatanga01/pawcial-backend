package com.pawcial.service

import com.pawcial.dto.BreedDto
import com.pawcial.dto.CreateBreedRequest
import com.pawcial.entity.core.Breed
import com.pawcial.entity.core.Species
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class BreedService {

    fun findAll(): List<BreedDto> {
        return Breed.findAll().list()
            .map { it.toDto() }
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
}

