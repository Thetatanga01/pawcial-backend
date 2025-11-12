package com.pawcial.service

import com.pawcial.dto.CreateSpeciesRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.SpeciesDto
import com.pawcial.dto.UpdateSpeciesRequest
import com.pawcial.entity.core.Species
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class SpeciesService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    fun findAll(all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<SpeciesDto> {
        val baseQuery = if (all) "1=1" else "isActive = true"
        val queryWithOrder = "$baseQuery ORDER BY commonName ASC"

        val totalElements = Species.count(baseQuery)

        val content = Species.find(queryWithOrder).page(page, size).list().map { it.toDto() }

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

        query += " ORDER BY commonName ASC"

        val totalElements = Species.count(query.replace(" ORDER BY commonName ASC", ""), params)

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
    fun hardDelete(id: UUID) {
        val species = Species.findById(id)
            ?: throw NotFoundException("Species not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = species.createdAt ?: throw IllegalStateException("Species has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        species.delete()
    }

    @Transactional
    fun delete(id: UUID) {
        val species = Species.findById(id)
            ?: throw NotFoundException("Species not found: $id")
        species.isActive = !species.isActive
        species.persist()
    }
}