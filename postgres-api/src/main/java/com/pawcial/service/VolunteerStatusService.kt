package com.pawcial.service

import com.pawcial.dto.VolunteerStatusDto
import com.pawcial.dto.CreateVolunteerStatusRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.VolunteerStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class VolunteerStatusService {

    fun findAll(all: Boolean = false): List<VolunteerStatusDto> {
        val query = if (all) {
            VolunteerStatus.findAll()
        } else {
            VolunteerStatus.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateVolunteerStatusRequest): VolunteerStatusDto {
        val existing = VolunteerStatus.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("VolunteerStatus with code '${request.code}' already exists")
        }

        val volunteerStatus = VolunteerStatus().apply {
            code = request.code
            label = request.label
        }
        volunteerStatus.persist()
        return volunteerStatus.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): VolunteerStatusDto {
        val volunteerStatus = VolunteerStatus.findById(code)
            ?: throw IllegalArgumentException("VolunteerStatus with code '$code' not found")

        volunteerStatus.label = request.label
        volunteerStatus.persist()
        return volunteerStatus.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val volunteerStatus = VolunteerStatus.findById(code) ?: return false
        volunteerStatus.isActive = !volunteerStatus.isActive
        volunteerStatus.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val volunteerStatus = VolunteerStatus.findById(code) ?: return false
        volunteerStatus.delete()
        return true
    }
}
