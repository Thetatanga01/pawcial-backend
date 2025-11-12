package com.pawcial.service

import com.pawcial.dto.AssetDto
import com.pawcial.dto.CreateAssetRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.UpdateAssetRequest
import com.pawcial.entity.core.Asset
import com.pawcial.entity.core.Facility
import com.pawcial.entity.core.FacilityUnit
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
class AssetService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    fun findAll(facilityId: UUID?, status: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<AssetDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (facilityId != null) {
            query += " and facility.id = :facilityId"
            params["facilityId"] = facilityId
        }

        if (!status.isNullOrBlank()) {
            query += " and lower(status) = lower(:status)"
            params["status"] = status
        }

        val totalElements = Asset.count(query, params)

        val content = Asset.find(query, params)
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

    fun search(name: String?, code: String?, type: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<AssetDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (!name.isNullOrBlank()) {
            query += " and lower(name) like lower(:name)"
            params["name"] = "%$name%"
        }

        if (!code.isNullOrBlank()) {
            query += " and lower(code) like lower(:code)"
            params["code"] = "%$code%"
        }

        if (!type.isNullOrBlank()) {
            query += " and lower(type) like lower(:type)"
            params["type"] = "%$type%"
        }

        val totalElements = Asset.count(query, params)

        val content = Asset.find(query, params)
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

    fun findById(id: UUID): AssetDto {
        return Asset.findById(id)?.toDto()
            ?: throw NotFoundException("Asset not found: $id")
    }

    @Transactional
    fun create(request: CreateAssetRequest): AssetDto {
        ValidationUtils.validateCode(request.code, "Asset code")

        val facility = Facility.findById(request.facilityId)
            ?: throw NotFoundException("Facility not found: ${request.facilityId}")

        val unit = request.unitId?.let {
            FacilityUnit.findById(it)
                ?: throw NotFoundException("FacilityUnit not found: $it")
        }

        val asset = Asset().apply {
            this.facility = facility
            this.unit = unit
            code = request.code
            name = request.name
            type = request.type
            serialNo = request.serialNo
            purchaseDate = request.purchaseDate
            warrantyEnd = request.warrantyEnd
            status = request.status
        }
        asset.persist()
        return asset.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdateAssetRequest): AssetDto {
        val asset = Asset.findById(id)
            ?: throw NotFoundException("Asset not found: $id")

        request.facilityId?.let {
            asset.facility = Facility.findById(it)
                ?: throw NotFoundException("Facility not found: $it")
        }

        request.unitId?.let {
            asset.unit = FacilityUnit.findById(it)
                ?: throw NotFoundException("FacilityUnit not found: $it")
        }

        asset.apply {
            request.code?.let { code = it }
            request.name?.let { name = it }
            request.type?.let { type = it }
            request.serialNo?.let { serialNo = it }
            request.purchaseDate?.let { purchaseDate = it }
            request.warrantyEnd?.let { warrantyEnd = it }
            request.status?.let { status = it }
        }
        asset.persist()
        return asset.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val asset = Asset.findById(id)
            ?: throw NotFoundException("Asset not found: $id")
        asset.isActive = !asset.isActive
        asset.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        val asset = Asset.findById(id)
            ?: throw NotFoundException("Asset not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = asset.createdAt ?: throw IllegalStateException("Asset has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        asset.delete()
    }
}
