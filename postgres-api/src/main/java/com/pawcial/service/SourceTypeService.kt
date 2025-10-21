package com.pawcial.service

import com.pawcial.dto.SourceTypeDto
import com.pawcial.entity.dictionary.SourceType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SourceTypeService {

    fun findAll(): List<SourceTypeDto> {
        return SourceType.findAll().list()
            .map { it.toDto() }
    }
}

