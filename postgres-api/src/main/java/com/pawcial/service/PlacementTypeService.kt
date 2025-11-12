package com.pawcial.service

import com.pawcial.dto.PlacementTypeDto
import com.pawcial.dto.CreatePlacementTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.PlacementType
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PlacementTypeService {

    fun findAll(all: Boolean = false): List<PlacementTypeDto> {
        val query = if (all) {
            PlacementType.findAll()
        } else {
            PlacementType.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreatePlacementTypeRequest): PlacementTypeDto {
        ValidationUtils.validateCode(request.code, "PlacementType code")

        val existing = PlacementType.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("PlacementType with code '${request.code}' already exists")
        }

        val placementType = PlacementType().apply {
            code = request.code
            label = request.label
        }
        placementType.persist()
        return placementType.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): PlacementTypeDto {
        val placementType = PlacementType.findById(code)
            ?: throw IllegalArgumentException("PlacementType with code '$code' not found")

        placementType.label = request.label
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

    @Transactional
    fun hardDelete(code: String): Boolean {
        val placementType = PlacementType.findById(code) ?: return false
        placementType.delete()
        return true
    }
}

