package com.pawcial.service

import com.pawcial.dto.CreateSpeciesRequest
import com.pawcial.dto.SpeciesDto
import com.pawcial.entity.core.Species
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class SpeciesService {

    fun findAll(): List<SpeciesDto> {
        return Species.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateSpeciesRequest): SpeciesDto {
        val species = Species().apply {
            scientificName = request.scientificName
            commonName = request.commonName
            domesticStatus = request.domesticStatus
        }
        species.persist()
        return species.toDto()
    }
}