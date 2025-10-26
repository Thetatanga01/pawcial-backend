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

    fun findAll(assetId: UUID?, all: Boolean = false): List<AssetServiceEntity> {
        val activeFilter = if (all) "" else " and isActive = true"
        return if (assetId != null) {
            AssetServiceEntity.find("asset.id = ?1$activeFilter", assetId).list()
        } else {
            if (all) {
                AssetServiceEntity.findAll().list()
            } else {
                AssetServiceEntity.find("isActive = true").list()
            }
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
        val service = AssetServiceEntity.findById(id)
            ?: throw NotFoundException("AssetService not found: $id")
        service.isActive = !service.isActive
        service.persist()
    }
}
