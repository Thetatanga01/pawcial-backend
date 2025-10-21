package com.pawcial.service

import com.pawcial.dto.OutcomeTypeDto
import com.pawcial.entity.dictionary.OutcomeType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class OutcomeTypeService {

    fun findAll(): List<OutcomeTypeDto> {
        return OutcomeType.findAll().list()
            .map { it.toDto() }
    }
}

