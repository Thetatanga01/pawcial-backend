package com.pawcial.service

import com.pawcial.dto.MedEventTypeDto
import com.pawcial.dto.CreateMedEventTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.MedEventType
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class MedEventTypeService {

    fun findAll(all: Boolean = false): List<MedEventTypeDto> {
        val query = if (all) {
            MedEventType.findAll()
        } else {
            MedEventType.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateMedEventTypeRequest): MedEventTypeDto {
        ValidationUtils.validateCode(request.code, "MedEventType code")

        val existing = MedEventType.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("MedEventType with code '${request.code}' already exists")
        }

        val medEventType = MedEventType().apply {
            code = request.code
            label = request.label
        }
        medEventType.persist()
        return medEventType.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): MedEventTypeDto {
        val medEventType = MedEventType.findById(code)
            ?: throw IllegalArgumentException("MedEventType with code '$code' not found")

        medEventType.label = request.label
        medEventType.persist()
        return medEventType.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val medEventType = MedEventType.findById(code) ?: return false
        medEventType.isActive = !medEventType.isActive
        medEventType.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val medEventType = MedEventType.findById(code) ?: return false
        medEventType.delete()
        return true
    }
}

