package com.pawcial.service

import com.pawcial.dto.CreateFacilityZoneRequest
import com.pawcial.dto.FacilityZoneDto
import com.pawcial.entity.core.Facility
import com.pawcial.entity.core.FacilityZone
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class FacilityZoneService {

    fun findAll(): List<FacilityZoneDto> {
        return FacilityZone.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateFacilityZoneRequest): FacilityZoneDto {
        val facility = Facility.findById(request.facilityId)
            ?: throw NotFoundException("Facility not found: ${request.facilityId}")

        val zone = FacilityZone().apply {
            this.facility = facility
            name = request.name
            purpose = request.purpose
        }
        zone.persist()
        return zone.toDto()
    }
}

