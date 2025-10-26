package com.pawcial.service

import com.pawcial.dto.CreateSpeciesRequest
import com.pawcial.dto.SpeciesDto
import com.pawcial.dto.UpdateSpeciesRequest
import com.pawcial.entity.core.Species
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class SpeciesService {

    fun findAll(): List<SpeciesDto> {
        return Species.findAll().list()
            .map { it.toDto() }
    }

    fun findById(id: UUID): SpeciesDto {
        return Species.findById(id)?.toDto()
            ?: throw NotFoundException("Species not found: $id")
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

    @Transactional
    fun update(id: UUID, request: UpdateSpeciesRequest): SpeciesDto {
        val species = Species.findById(id)
            ?: throw NotFoundException("Species not found: $id")

        species.apply {
            request.scientificName?.let { scientificName = it }
            request.commonName?.let { commonName = it }
            request.domesticStatus?.let { domesticStatus = it }
        }
        species.persist()
        return species.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val deleted = Species.deleteById(id)
        if (!deleted) {
            throw NotFoundException("Species not found: $id")
        }
    }
}