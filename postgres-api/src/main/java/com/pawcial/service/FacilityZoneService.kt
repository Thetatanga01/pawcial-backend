package com.pawcial.service

import com.pawcial.dto.CreateFacilityZoneRequest
import com.pawcial.dto.FacilityZoneDto
import com.pawcial.dto.UpdateFacilityZoneRequest
import com.pawcial.entity.core.Facility
import com.pawcial.entity.core.FacilityZone
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class FacilityZoneService {

    fun findAll(): List<FacilityZoneDto> {
        return FacilityZone.findAll().list()
            .map { it.toDto() }
    }

    fun findById(id: UUID): FacilityZoneDto {
        return FacilityZone.findById(id)?.toDto()
            ?: throw NotFoundException("FacilityZone not found: $id")
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

    @Transactional
    fun update(id: UUID, request: UpdateFacilityZoneRequest): FacilityZoneDto {
        val zone = FacilityZone.findById(id)
            ?: throw NotFoundException("FacilityZone not found: $id")

        request.facilityId?.let {
            zone.facility = Facility.findById(it)
                ?: throw NotFoundException("Facility not found: $it")
        }

        zone.apply {
            request.name?.let { name = it }
            request.purpose?.let { purpose = it }
        }
        zone.persist()
        return zone.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val deleted = FacilityZone.deleteById(id)
        if (!deleted) {
            throw NotFoundException("FacilityZone not found: $id")
        }
    }
}
