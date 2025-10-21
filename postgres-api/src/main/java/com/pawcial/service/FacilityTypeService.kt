package com.pawcial.service

import com.pawcial.dto.FacilityTypeDto
import com.pawcial.dto.CreateFacilityTypeRequest
import com.pawcial.entity.dictionary.FacilityType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class FacilityTypeService {

    fun findAll(all: Boolean = false): List<FacilityTypeDto> {
        return if (all) {
            FacilityType.findAll().list()
        } else {
            FacilityType.find("isActive", true).list()
        }.map { it.toDto() }
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
        facilityType.persist()
        return true
    }
}

