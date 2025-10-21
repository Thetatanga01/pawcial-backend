package com.pawcial.service

import com.pawcial.dto.AssetTypeDto
import com.pawcial.dto.CreateAssetTypeRequest
import com.pawcial.entity.dictionary.AssetType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class AssetTypeService {

    fun findAll(all: Boolean = false): List<AssetTypeDto> {
        return if (all) {
            AssetType.findAll().list()
        } else {
            AssetType.find("isActive", true).list()
        }.map { it.toDto() }
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
        assetType.persist()
        return true
    }
}

