package com.pawcial.service

import com.pawcial.dto.FacilityTypeDto
import com.pawcial.dto.CreateFacilityTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.FacilityType
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class FacilityTypeService {

    fun findAll(all: Boolean = false): List<FacilityTypeDto> {
        val query = if (all) {
            FacilityType.findAll()
        } else {
            FacilityType.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateFacilityTypeRequest): FacilityTypeDto {
        ValidationUtils.validateCode(request.code, "FacilityType code")

        val existing = FacilityType.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("FacilityType with code '${request.code}' already exists")
        }

        val facilityType = FacilityType().apply {
            code = request.code
            label = request.label
        }
        facilityType.persist()
        return facilityType.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): FacilityTypeDto {
        val facilityType = FacilityType.findById(code)
            ?: throw IllegalArgumentException("FacilityType with code '$code' not found")

        facilityType.label = request.label
        facilityType.persist()
        return facilityType.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val facilityType = FacilityType.findById(code) ?: return false
        facilityType.isActive = !facilityType.isActive
        facilityType.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val facilityType = FacilityType.findById(code) ?: return false
        facilityType.delete()
        return true
    }
}

