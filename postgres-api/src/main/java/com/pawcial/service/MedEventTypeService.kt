package com.pawcial.service

import com.pawcial.dto.MedEventTypeDto
import com.pawcial.dto.CreateMedEventTypeRequest
import com.pawcial.entity.dictionary.MedEventType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class MedEventTypeService {

    fun findAll(): List<MedEventTypeDto> {
        return MedEventType.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateMedEventTypeRequest): MedEventTypeDto {
        val medEventType = MedEventType().apply {
            code = request.code
            label = request.label
        }
        medEventType.persist()
        return medEventType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val medEventType = MedEventType.findById(code) ?: return false
        medEventType.isActive = !medEventType.isActive
        return true
    }
}

