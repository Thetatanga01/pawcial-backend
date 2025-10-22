package com.pawcial.service

import com.pawcial.dto.CreateFacilityUnitRequest
import com.pawcial.dto.FacilityUnitDto
import com.pawcial.entity.core.Facility
import com.pawcial.entity.core.FacilityUnit
import com.pawcial.entity.core.FacilityZone
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class FacilityUnitService {

    fun findAll(): List<FacilityUnitDto> {
        return FacilityUnit.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateFacilityUnitRequest): FacilityUnitDto {
        val facility = Facility.findById(request.facilityId)
            ?: throw NotFoundException("Facility not found: ${request.facilityId}")

        val zone = request.zoneId?.let {
            FacilityZone.findById(it)
                ?: throw NotFoundException("FacilityZone not found: $it")
        }

        val unit = FacilityUnit().apply {
            this.facility = facility
            this.zone = zone
            code = request.code
            type = request.type
            capacity = request.capacity
        }
        unit.persist()
        return unit.toDto()
    }
}

