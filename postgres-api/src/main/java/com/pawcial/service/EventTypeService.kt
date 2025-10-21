package com.pawcial.service

import com.pawcial.dto.EventTypeDto
import com.pawcial.dto.CreateEventTypeRequest
import com.pawcial.entity.dictionary.EventType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class EventTypeService {

    fun findAll(all: Boolean = false): List<EventTypeDto> {
        return if (all) {
            EventType.findAll().list()
        } else {
            EventType.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateEventTypeRequest): EventTypeDto {
        val eventType = EventType().apply {
            code = request.code
            label = request.label
        }
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
}

