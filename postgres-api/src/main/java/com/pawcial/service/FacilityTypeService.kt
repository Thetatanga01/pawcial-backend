package com.pawcial.service

import com.pawcial.dto.FacilityTypeDto
import com.pawcial.dto.CreateFacilityTypeRequest
import com.pawcial.entity.dictionary.FacilityType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class FacilityTypeService {

    fun findAll(): List<FacilityTypeDto> {
        return FacilityType.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateFacilityTypeRequest): FacilityTypeDto {
        val facilityType = FacilityType().apply {
            code = request.code
            label = request.label
        }
        facilityType.persist()
        return facilityType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val facilityType = FacilityType.findById(code) ?: return false
        facilityType.isActive = !facilityType.isActive
        return true
    }
}

