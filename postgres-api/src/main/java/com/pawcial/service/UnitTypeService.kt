package com.pawcial.service

import com.pawcial.dto.UnitTypeDto
import com.pawcial.dto.CreateUnitTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.UnitType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class UnitTypeService {

    fun findAll(all: Boolean = false): List<UnitTypeDto> {
        return if (all) {
            UnitType.findAll().list()
        } else {
            UnitType.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateUnitTypeRequest): UnitTypeDto {
        val existing = UnitType.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("UnitType with code '${request.code}' already exists")
        }

        val unitType = UnitType().apply {
            code = request.code
            label = request.label
        }
        unitType.persist()
        return unitType.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): UnitTypeDto {
        val unitType = UnitType.findById(code)
            ?: throw IllegalArgumentException("UnitType with code '$code' not found")

        unitType.label = request.label
        unitType.persist()
        return unitType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val unitType = UnitType.findById(code) ?: return false
        unitType.isActive = !unitType.isActive
        unitType.persist()
        return true
    }
}
