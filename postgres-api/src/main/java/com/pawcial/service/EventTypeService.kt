package com.pawcial.service

import com.pawcial.dto.EventTypeDto
import com.pawcial.dto.CreateEventTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.EventType
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class EventTypeService {

    fun findAll(all: Boolean = false): List<EventTypeDto> {
        val query = if (all) {
            EventType.findAll()
        } else {
            EventType.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateEventTypeRequest): EventTypeDto {
        ValidationUtils.validateCode(request.code, "EventType code")

        val existing = EventType.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("EventType with code '${request.code}' already exists")
        }

        val eventType = EventType().apply {
            code = request.code
            label = request.label
        }
        eventType.persist()
        return eventType.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): EventTypeDto {
        val eventType = EventType.findById(code)
            ?: throw IllegalArgumentException("EventType with code '$code' not found")

        eventType.label = request.label
        eventType.persist()
        return eventType.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val eventType = EventType.findById(code) ?: return false
        eventType.isActive = !eventType.isActive
        eventType.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val eventType = EventType.findById(code) ?: return false
        eventType.delete()
        return true
    }
}

