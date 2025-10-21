package com.pawcial.service

import com.pawcial.dto.FacilityZoneDto
import com.pawcial.entity.core.FacilityZone
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class FacilityZoneService {

    fun findAll(): List<FacilityZoneDto> {
        return FacilityZone.findAll().list()
            .map { it.toDto() }
    }
}

