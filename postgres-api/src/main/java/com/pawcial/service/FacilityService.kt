package com.pawcial.service

import com.pawcial.dto.CreateFacilityRequest
import com.pawcial.dto.FacilityDto
import com.pawcial.entity.core.Facility
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class FacilityService {

    fun findAll(): List<FacilityDto> {
        return Facility.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateFacilityRequest): FacilityDto {
        val facility = Facility().apply {
            name = request.name
            type = request.type
            country = request.country
            city = request.city
            address = request.address
        }
        facility.persist()
        return facility.toDto()
    }
}

