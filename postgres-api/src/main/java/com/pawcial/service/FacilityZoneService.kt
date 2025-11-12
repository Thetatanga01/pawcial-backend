package com.pawcial.service

import com.pawcial.dto.CreateFacilityZoneRequest
import com.pawcial.dto.FacilityZoneDto
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.UpdateFacilityZoneRequest
import com.pawcial.entity.core.Facility
import com.pawcial.entity.core.FacilityZone
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class FacilityZoneService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    fun findAll(all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<FacilityZoneDto> {
        val query = if (all) {
            FacilityZone.findAll()
        } else {
            FacilityZone.find("isActive = true")
        }

        val totalCount = query.count()
        val zones = query.page(page, size).list()

        return PagedResponse(
            content = zones.map { it.toDto() },
            page = page,
            size = size,
            totalElements = totalCount,
            totalPages = ((totalCount + size - 1) / size).toInt(),
            hasNext = page < ((totalCount + size - 1) / size).toInt() - 1,
            hasPrevious = page > 0
        )
    }

    fun search(
        name: String?,
        description: String?,
        facilityId: UUID?,
        all: Boolean = false,
        page: Int = 0,
        size: Int = 20
    ): PagedResponse<FacilityZoneDto> {
        val conditions = mutableListOf<String>()
        val params = mutableMapOf<String, Any>()

        if (!all) {
            conditions.add("isActive = true")
        }

        name?.let {
            conditions.add("LOWER(name) LIKE LOWER(:name)")
            params["name"] = "%$it%"
        }

        description?.let {
            conditions.add("LOWER(purpose) LIKE LOWER(:description)")
            params["description"] = "%$it%"
        }

        facilityId?.let {
            conditions.add("facility.id = :facilityId")
            params["facilityId"] = it
        }

        val whereClause = if (conditions.isEmpty()) "" else conditions.joinToString(" AND ")
        val query = if (whereClause.isEmpty()) {
            FacilityZone.findAll()
        } else {
            FacilityZone.find(whereClause, params)
        }

        val totalCount = query.count()
        val zones = query.page(page, size).list()

        return PagedResponse(
            content = zones.map { it.toDto() },
            page = page,
            size = size,
            totalElements = totalCount,
            totalPages = ((totalCount + size - 1) / size).toInt(),
            hasNext = page < ((totalCount + size - 1) / size).toInt() - 1,
            hasPrevious = page > 0
        )
    }

    fun findById(id: UUID): FacilityZoneDto {
        return FacilityZone.findById(id)?.toDto()
            ?: throw NotFoundException("FacilityZone not found: $id")
    }

    @Transactional
    fun create(request: CreateFacilityZoneRequest): FacilityZoneDto {
        val facility = Facility.findById(request.facilityId)
            ?: throw NotFoundException("Facility not found: ${request.facilityId}")

        val zone = FacilityZone().apply {
            this.facility = facility
            name = request.name
            purpose = request.purpose
        }
        zone.persist()
        return zone.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdateFacilityZoneRequest): FacilityZoneDto {
        val zone = FacilityZone.findById(id)
            ?: throw NotFoundException("FacilityZone not found: $id")

        request.facilityId?.let {
            zone.facility = Facility.findById(it)
                ?: throw NotFoundException("Facility not found: $it")
        }

        zone.apply {
            request.name?.let { name = it }
            request.purpose?.let { purpose = it }
        }
        zone.persist()
        return zone.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val zone = FacilityZone.findById(id)
            ?: throw NotFoundException("FacilityZone not found: $id")
        zone.isActive = !zone.isActive
        zone.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        val zone = FacilityZone.findById(id)
            ?: throw NotFoundException("FacilityZone not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = zone.createdAt ?: throw IllegalStateException("FacilityZone has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        zone.delete()
    }
}
