package com.pawcial.service

import com.pawcial.dto.CreateAssetServiceRequest
import com.pawcial.entity.core.Asset
import com.pawcial.entity.core.AssetService as AssetServiceEntity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class AssetServiceService {

    @Transactional
    fun create(request: CreateAssetServiceRequest): AssetServiceEntity {
        val asset = Asset.findById(request.assetId)
            ?: throw NotFoundException("Asset not found: ${request.assetId}")

        val service = AssetServiceEntity().apply {
            this.asset = asset
            serviceAt = request.serviceAt
            serviceType = request.serviceType
            vendor = request.vendor
            cost = request.cost
            notes = request.notes
        }
        service.persist()
        return service
    }
}
