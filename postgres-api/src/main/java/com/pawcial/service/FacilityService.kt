package com.pawcial.service

import com.pawcial.dto.CreateFacilityRequest
import com.pawcial.dto.FacilityDto
import com.pawcial.dto.UpdateFacilityRequest
import com.pawcial.entity.core.Facility
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class FacilityService {

    fun findAll(all: Boolean = false): List<FacilityDto> {
        return if (all) {
            Facility.findAll().list().map { it.toDto() }
        } else {
            Facility.find("isActive = true").list().map { it.toDto() }
        }
    }

    fun findById(id: UUID): FacilityDto {
        return Facility.findById(id)?.toDto()
            ?: throw NotFoundException("Facility not found: $id")
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

    @Transactional
    fun update(id: UUID, request: UpdateFacilityRequest): FacilityDto {
        val facility = Facility.findById(id)
            ?: throw NotFoundException("Facility not found: $id")

        facility.apply {
            request.name?.let { name = it }
            request.type?.let { type = it }
            request.country?.let { country = it }
            request.city?.let { city = it }
            request.address?.let { address = it }
        }
        facility.persist()
        return facility.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val facility = Facility.findById(id)
            ?: throw NotFoundException("Facility not found: $id")
        facility.isActive = !facility.isActive
        facility.persist()
    }
}
