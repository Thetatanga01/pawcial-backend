package com.pawcial.service

import com.pawcial.dto.EventTypeDto
import com.pawcial.entity.dictionary.EventType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class EventTypeService {

    fun findAll(): List<EventTypeDto> {
        return EventType.findAll().list()
            .map { it.toDto() }
    }
}

