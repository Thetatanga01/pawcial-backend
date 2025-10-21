package com.pawcial.service

import com.pawcial.dto.AssetStatusDto
import com.pawcial.dto.CreateAssetStatusRequest
import com.pawcial.entity.dictionary.AssetStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class AssetStatusService {

    fun findAll(all: Boolean = false): List<AssetStatusDto> {
        return if (all) {
            AssetStatus.findAll().list()
        } else {
            AssetStatus.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateAssetStatusRequest): AssetStatusDto {
        val assetStatus = AssetStatus().apply {
            code = request.code
            label = request.label
        }
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
}
