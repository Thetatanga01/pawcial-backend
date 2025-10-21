package com.pawcial.service

import com.pawcial.dto.PlacementStatusDto
import com.pawcial.dto.CreatePlacementStatusRequest
import com.pawcial.entity.dictionary.PlacementStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PlacementStatusService {

    fun findAll(): List<PlacementStatusDto> {
        return PlacementStatus.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreatePlacementStatusRequest): PlacementStatusDto {
        val placementStatus = PlacementStatus().apply {
            code = request.code
            label = request.label
        }
        placementStatus.persist()
        return placementStatus.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val placementStatus = PlacementStatus.findById(code) ?: return false
        placementStatus.isActive = !placementStatus.isActive
        return true
    }
}

