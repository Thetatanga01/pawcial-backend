package com.pawcial.service

import com.pawcial.dto.VolunteerAreaDto
import com.pawcial.dto.CreateVolunteerAreaRequest
import com.pawcial.entity.dictionary.VolunteerAreaDictionary
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class VolunteerAreaService {

    fun findAll(all: Boolean = false): List<VolunteerAreaDto> {
        return if (all) {
            VolunteerAreaDictionary.findAll().list()
        } else {
            VolunteerAreaDictionary.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateVolunteerAreaRequest): VolunteerAreaDto {
        val volunteerArea = VolunteerAreaDictionary().apply {
            code = request.code
            label = request.label
            description = request.description
        }
        volunteerArea.persist()
        return volunteerArea.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val volunteerArea = VolunteerAreaDictionary.findById(code) ?: return false
        volunteerArea.isActive = !volunteerArea.isActive
        volunteerArea.persist()
        return true
    }
}

