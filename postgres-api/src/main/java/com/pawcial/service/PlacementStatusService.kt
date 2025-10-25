package com.pawcial.service

import com.pawcial.dto.PlacementStatusDto
import com.pawcial.dto.CreatePlacementStatusRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.PlacementStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PlacementStatusService {

    fun findAll(all: Boolean = false): List<PlacementStatusDto> {
        return if (all) {
            PlacementStatus.findAll().list()
        } else {
            PlacementStatus.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreatePlacementStatusRequest): PlacementStatusDto {
        val existing = PlacementStatus.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("PlacementStatus with code '${request.code}' already exists")
        }

        val placementStatus = PlacementStatus().apply {
            code = request.code
            label = request.label
        }
        placementStatus.persist()
        return placementStatus.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): PlacementStatusDto {
        val placementStatus = PlacementStatus.findById(code)
            ?: throw IllegalArgumentException("PlacementStatus with code '$code' not found")

        placementStatus.label = request.label
        placementStatus.persist()
        return placementStatus.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val placementStatus = PlacementStatus.findById(code) ?: return false
        placementStatus.isActive = !placementStatus.isActive
        placementStatus.persist()
        return true
    }
}

