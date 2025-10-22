package com.pawcial.service

import com.pawcial.dto.CreateAssetServiceRequest
import com.pawcial.entity.core.Asset
import com.pawcial.entity.core.AssetService as AssetServiceEntity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class AssetServiceService {

    fun findAll(assetId: UUID?): List<AssetServiceEntity> {
        return if (assetId != null) {
            AssetServiceEntity.find("asset.id", assetId).list()
        } else {
            AssetServiceEntity.findAll().list()
        }
    }

    fun findById(id: UUID): AssetServiceEntity {
        return AssetServiceEntity.findById(id)
            ?: throw NotFoundException("AssetService not found: $id")
    }

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

    @Transactional
    fun delete(id: UUID) {
        val deleted = AssetServiceEntity.deleteById(id)
        if (!deleted) {
            throw NotFoundException("AssetService not found: $id")
        }
    }
}
