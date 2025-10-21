package com.pawcial.service

import com.pawcial.dto.FacilityDto
import com.pawcial.entity.core.Facility
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class FacilityService {

    fun findAll(): List<FacilityDto> {
        return Facility.findAll().list()
            .map { it.toDto() }
    }
}

