package com.pawcial.service

import com.pawcial.dto.CreateFacilityUnitRequest
import com.pawcial.dto.FacilityUnitDto
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.UpdateFacilityUnitRequest
import com.pawcial.entity.core.Facility
import com.pawcial.entity.core.FacilityUnit
import com.pawcial.entity.core.FacilityZone
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class FacilityUnitService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    fun findAll(facilityId: UUID?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<FacilityUnitDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (facilityId != null) {
            query += " and facility.id = :facilityId"
            params["facilityId"] = facilityId
        }

        val totalElements = FacilityUnit.count(query, params)

        val content = FacilityUnit.find(query, params)
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

    fun search(code: String?, type: String?, facilityName: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<FacilityUnitDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (!code.isNullOrBlank()) {
            query += " and lower(code) like lower(:code)"
            params["code"] = "%$code%"
        }

        if (!type.isNullOrBlank()) {
            query += " and lower(type) like lower(:type)"
            params["type"] = "%$type%"
        }

        if (!facilityName.isNullOrBlank()) {
            query += " and lower(facility.name) like lower(:facilityName)"
            params["facilityName"] = "%$facilityName%"
        }

        val totalElements = FacilityUnit.count(query, params)

        val content = FacilityUnit.find(query, params)
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

    fun findById(id: UUID): FacilityUnitDto {
        return FacilityUnit.findById(id)?.toDto()
            ?: throw NotFoundException("FacilityUnit not found: $id")
    }

    @Transactional
    fun create(request: CreateFacilityUnitRequest): FacilityUnitDto {
        ValidationUtils.validateCode(request.code, "FacilityUnit code")

        val facility = Facility.findById(request.facilityId)
            ?: throw NotFoundException("Facility not found: ${request.facilityId}")

        val zone = request.zoneId?.let {
            FacilityZone.findById(it)
                ?: throw NotFoundException("FacilityZone not found: $it")
        }

        val unit = FacilityUnit().apply {
            this.facility = facility
            this.zone = zone
            code = request.code
            type = request.type
            capacity = request.capacity
        }
        unit.persist()
        return unit.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdateFacilityUnitRequest): FacilityUnitDto {
        val unit = FacilityUnit.findById(id)
            ?: throw NotFoundException("FacilityUnit not found: $id")

        request.facilityId?.let {
            unit.facility = Facility.findById(it)
                ?: throw NotFoundException("Facility not found: $it")
        }

        request.zoneId?.let {
            unit.zone = FacilityZone.findById(it)
                ?: throw NotFoundException("FacilityZone not found: $it")
        }

        request.code?.let {
            ValidationUtils.validateCode(it, "FacilityUnit code")
            unit.code = it
        }

        request.type?.let { unit.type = it }
        request.capacity?.let { unit.capacity = it }

        unit.persist()
        return unit.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val unit = FacilityUnit.findById(id)
            ?: throw NotFoundException("FacilityUnit not found: $id")
        unit.isActive = !unit.isActive
        unit.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        val unit = FacilityUnit.findById(id)
            ?: throw NotFoundException("FacilityUnit not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = unit.createdAt ?: throw IllegalStateException("FacilityUnit has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        unit.delete()
    }
}

