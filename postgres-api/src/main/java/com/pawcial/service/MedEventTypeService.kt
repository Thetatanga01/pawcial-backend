package com.pawcial.service

import com.pawcial.dto.MedEventTypeDto
import com.pawcial.entity.dictionary.MedEventType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class MedEventTypeService {

    fun findAll(): List<MedEventTypeDto> {
        return MedEventType.findAll().list()
            .map { it.toDto() }
    }
}

