package com.pawcial.service

import com.pawcial.dto.AssetDto
import com.pawcial.dto.CreateAssetRequest
import com.pawcial.dto.UpdateAssetRequest
import com.pawcial.entity.core.Asset
import com.pawcial.entity.core.Facility
import com.pawcial.entity.core.FacilityUnit
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class AssetService {

    fun findAll(all: Boolean = false): List<AssetDto> {
        return if (all) {
            Asset.findAll().list().map { it.toDto() }
        } else {
            Asset.find("isActive = true").list().map { it.toDto() }
        }
    }

    fun findById(id: UUID): AssetDto {
        return Asset.findById(id)?.toDto()
            ?: throw NotFoundException("Asset not found: $id")
    }

    @Transactional
    fun create(request: CreateAssetRequest): AssetDto {
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
}
