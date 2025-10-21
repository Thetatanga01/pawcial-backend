package com.pawcial.service

import com.pawcial.dto.AssetTypeDto
import com.pawcial.dto.CreateAssetTypeRequest
import com.pawcial.entity.dictionary.AssetType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class AssetTypeService {

    fun findAll(): List<AssetTypeDto> {
        return AssetType.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateAssetTypeRequest): AssetTypeDto {
        val assetType = AssetType().apply {
            code = request.code
            label = request.label
        }
        assetType.persist()
        return assetType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val assetType = AssetType.findById(code) ?: return false
        assetType.isActive = !assetType.isActive
        return true
    }
}

