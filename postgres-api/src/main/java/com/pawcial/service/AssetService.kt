package com.pawcial.service

import com.pawcial.dto.AssetDto
import com.pawcial.dto.CreateAssetRequest
import com.pawcial.entity.core.Asset
import com.pawcial.entity.core.Facility
import com.pawcial.entity.core.FacilityUnit
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class AssetService {

    fun findAll(): List<AssetDto> {
        return Asset.findAll().list()
            .map { it.toDto() }
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
}


