package com.pawcial.service

import com.pawcial.dto.UnitTypeDto
import com.pawcial.dto.CreateUnitTypeRequest
import com.pawcial.entity.dictionary.UnitType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class UnitTypeService {

    fun findAll(): List<UnitTypeDto> {
        return UnitType.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateUnitTypeRequest): UnitTypeDto {
        val unitType = UnitType().apply {
            code = request.code
            label = request.label
        }
        unitType.persist()
        return unitType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val unitType = UnitType.findById(code) ?: return false
        unitType.isActive = !unitType.isActive
        return true
    }
}

