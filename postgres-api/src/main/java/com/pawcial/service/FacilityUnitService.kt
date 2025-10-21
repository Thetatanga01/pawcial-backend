package com.pawcial.service

import com.pawcial.dto.FacilityUnitDto
import com.pawcial.entity.core.FacilityUnit
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class FacilityUnitService {

    fun findAll(): List<FacilityUnitDto> {
        return FacilityUnit.findAll().list()
            .map { it.toDto() }
    }
}

