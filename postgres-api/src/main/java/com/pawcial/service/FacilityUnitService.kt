package com.pawcial.service

import com.pawcial.dto.CreateFacilityUnitRequest
import com.pawcial.dto.FacilityUnitDto
import com.pawcial.dto.PagedResponse
import com.pawcial.entity.core.Facility
import com.pawcial.entity.core.FacilityUnit
import com.pawcial.entity.core.FacilityZone
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class FacilityUnitService {

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
}

