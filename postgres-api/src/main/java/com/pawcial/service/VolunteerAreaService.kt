package com.pawcial.service

import com.pawcial.dto.VolunteerAreaDto
import com.pawcial.dto.CreateVolunteerAreaDictionaryRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.VolunteerAreaDictionary
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
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
    fun create(request: CreateVolunteerAreaDictionaryRequest): VolunteerAreaDto {
        ValidationUtils.validateCode(request.code, "VolunteerArea code")

        val existing = VolunteerAreaDictionary.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("VolunteerArea with code '${request.code}' already exists")
        }

        val volunteerArea = VolunteerAreaDictionary().apply {
            code = request.code
            label = request.label
            description = request.description
        }
        volunteerArea.persist()
        return volunteerArea.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): VolunteerAreaDto {
        val volunteerArea = VolunteerAreaDictionary.findById(code)
            ?: throw IllegalArgumentException("VolunteerArea with code '$code' not found")

        volunteerArea.label = request.label
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

