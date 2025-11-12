package com.pawcial.service

import com.pawcial.dto.CreateFacilityRequest
import com.pawcial.dto.FacilityDto
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.UpdateFacilityRequest
import com.pawcial.entity.core.Facility
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class FacilityService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    fun findAll(all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<FacilityDto> {
        val query = if (all) "1=1" else "isActive = true"

        val totalElements = Facility.count(query)

        val content = if (all) {
            Facility.findAll().page(page, size).list().map { it.toDto() }
        } else {
            Facility.find("isActive = true").page(page, size).list().map { it.toDto() }
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

    fun search(name: String?, city: String?, type: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<FacilityDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (!name.isNullOrBlank()) {
            query += " and lower(name) like lower(:name)"
            params["name"] = "%$name%"
        }

        if (!city.isNullOrBlank()) {
            query += " and lower(city) like lower(:city)"
            params["city"] = "%$city%"
        }

        if (!type.isNullOrBlank()) {
            query += " and lower(type) like lower(:type)"
            params["type"] = "%$type%"
        }

        val totalElements = Facility.count(query, params)

        val content = Facility.find(query, params)
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

    fun findById(id: UUID): FacilityDto {
        return Facility.findById(id)?.toDto()
            ?: throw NotFoundException("Facility not found: $id")
    }

    @Transactional
    fun create(request: CreateFacilityRequest): FacilityDto {
        val facility = Facility().apply {
            name = request.name
            type = request.type
            country = request.country
            city = request.city
            address = request.address
        }
        facility.persist()
        return facility.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdateFacilityRequest): FacilityDto {
        val facility = Facility.findById(id)
            ?: throw NotFoundException("Facility not found: $id")

        facility.apply {
            request.name?.let { name = it }
            request.type?.let { type = it }
            request.country?.let { country = it }
            request.city?.let { city = it }
            request.address?.let { address = it }
        }
        facility.persist()
        return facility.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val facility = Facility.findById(id)
            ?: throw NotFoundException("Facility not found: $id")
        facility.isActive = !facility.isActive
        facility.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        val facility = Facility.findById(id)
            ?: throw NotFoundException("Facility not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = facility.createdAt ?: throw IllegalStateException("Facility has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        facility.delete()
    }
}
