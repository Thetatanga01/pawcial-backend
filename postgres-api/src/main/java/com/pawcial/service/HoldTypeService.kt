package com.pawcial.service

import com.pawcial.dto.HoldTypeDto
import com.pawcial.entity.dictionary.HoldType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class HoldTypeService {

    fun findAll(): List<HoldTypeDto> {
        return HoldType.findAll().list()
            .map { it.toDto() }
    }
}

