package com.pawcial.service

import com.pawcial.dto.AssetTypeDto
import com.pawcial.dto.CreateAssetTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.AssetType
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class AssetTypeService {

    fun findAll(all: Boolean = false): List<AssetTypeDto> {
        val query = if (all) {
            AssetType.findAll()
        } else {
            AssetType.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateAssetTypeRequest): AssetTypeDto {
        ValidationUtils.validateCode(request.code, "AssetType code")

        val existing = AssetType.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("AssetType with code '${request.code}' already exists")
        }

        val assetType = AssetType().apply {
            code = request.code
            label = request.label
        }
        assetType.persist()
        return assetType.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): AssetTypeDto {
        val assetType = AssetType.findById(code)
            ?: throw IllegalArgumentException("AssetType with code '$code' not found")

        assetType.label = request.label
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

    @Transactional
    fun hardDelete(code: String): Boolean {
        val assetType = AssetType.findById(code) ?: return false
        assetType.delete()
        return true
    }
}

