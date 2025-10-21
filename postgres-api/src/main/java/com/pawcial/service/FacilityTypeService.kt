package com.pawcial.service

import com.pawcial.dto.FacilityTypeDto
import com.pawcial.entity.dictionary.FacilityType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class FacilityTypeService {

    fun findAll(): List<FacilityTypeDto> {
        return FacilityType.findAll().list()
            .map { it.toDto() }
    }
}

