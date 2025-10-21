package com.pawcial.service

import com.pawcial.dto.PlacementTypeDto
import com.pawcial.dto.CreatePlacementTypeRequest
import com.pawcial.entity.dictionary.PlacementType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PlacementTypeService {

    fun findAll(all: Boolean = false): List<PlacementTypeDto> {
        return if (all) {
            PlacementType.findAll().list()
        } else {
            PlacementType.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreatePlacementTypeRequest): PlacementTypeDto {
        val placementType = PlacementType().apply {
            code = request.code
            label = request.label
        }
        placementType.persist()
        return placementType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val placementType = PlacementType.findById(code) ?: return false
        placementType.isActive = !placementType.isActive
        placementType.persist()
        return true
    }
}

