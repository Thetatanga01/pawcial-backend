package com.pawcial.service

import com.pawcial.dto.CreateSpeciesRequest
import com.pawcial.dto.PagedResponse
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

    fun findAll(all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<SpeciesDto> {
        val query = if (all) "1=1" else "isActive = true"

        val totalElements = Species.count(query)

        val content = if (all) {
            Species.findAll().page(page, size).list().map { it.toDto() }
        } else {
            Species.find("isActive = true").page(page, size).list().map { it.toDto() }
        }

        val totalPages = ((totalElements + size - 1) / size).toInt()

        return PagedResponse(
            content = content,
            page = page,
            size = size,
            totalElements = totalElements,
            totalPages = totalPages,
            hasNext = page < totalPages - 1,
            hasPrevious = page > 0
        )
    }

    fun search(scientificName: String?, commonName: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<SpeciesDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (!scientificName.isNullOrBlank()) {
            query += " and lower(scientificName) like lower(:scientificName)"
            params["scientificName"] = "%$scientificName%"
        }

        if (!commonName.isNullOrBlank()) {
            query += " and lower(commonName) like lower(:commonName)"
            params["commonName"] = "%$commonName%"
        }

        val totalElements = Species.count(query, params)

        val content = Species.find(query, params)
            .page(page, size)
            .list()
            .map { it.toDto() }

        val totalPages = ((totalElements + size - 1) / size).toInt()

        return PagedResponse(
            content = content,
            page = page,
            size = size,
            totalElements = totalElements,
            totalPages = totalPages,
            hasNext = page < totalPages - 1,
            hasPrevious = page > 0
        )
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
        val species = Species.findById(id)
            ?: throw NotFoundException("Species not found: $id")
        species.isActive = !species.isActive
        species.persist()
    }
}