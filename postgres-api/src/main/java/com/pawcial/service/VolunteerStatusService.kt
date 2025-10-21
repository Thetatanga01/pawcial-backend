package com.pawcial.service

import com.pawcial.dto.VolunteerStatusDto
import com.pawcial.dto.CreateVolunteerStatusRequest
import com.pawcial.entity.dictionary.VolunteerStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class VolunteerStatusService {

    fun findAll(): List<VolunteerStatusDto> {
        return VolunteerStatus.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateVolunteerStatusRequest): VolunteerStatusDto {
        val volunteerStatus = VolunteerStatus().apply {
            code = request.code
            label = request.label
        }
        volunteerStatus.persist()
        return volunteerStatus.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val volunteerStatus = VolunteerStatus.findById(code) ?: return false
        volunteerStatus.isActive = !volunteerStatus.isActive
        return true
    }
}

