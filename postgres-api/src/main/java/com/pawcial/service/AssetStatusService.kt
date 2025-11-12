package com.pawcial.service

import com.pawcial.dto.AssetStatusDto
import com.pawcial.dto.CreateAssetStatusRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.AssetStatus
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class AssetStatusService {

    fun findAll(all: Boolean = false): List<AssetStatusDto> {
        val query = if (all) {
            AssetStatus.findAll()
        } else {
            AssetStatus.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateAssetStatusRequest): AssetStatusDto {
        ValidationUtils.validateCode(request.code, "AssetStatus code")

        val existing = AssetStatus.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("AssetStatus with code '${request.code}' already exists")
        }

        val assetStatus = AssetStatus().apply {
            code = request.code
            label = request.label
        }
        assetStatus.persist()
        return assetStatus.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): AssetStatusDto {
        val assetStatus = AssetStatus.findById(code)
            ?: throw IllegalArgumentException("AssetStatus with code '$code' not found")

        assetStatus.label = request.label
        assetStatus.persist()
        return assetStatus.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val assetStatus = AssetStatus.findById(code) ?: return false
        assetStatus.isActive = !assetStatus.isActive
        assetStatus.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val assetStatus = AssetStatus.findById(code) ?: return false
        assetStatus.delete()
        return true
    }
}
