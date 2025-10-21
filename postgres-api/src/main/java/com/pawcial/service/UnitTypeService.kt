package com.pawcial.service

import com.pawcial.dto.UnitTypeDto
import com.pawcial.entity.dictionary.UnitType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UnitTypeService {

    fun findAll(): List<UnitTypeDto> {
        return UnitType.findAll().list()
            .map { it.toDto() }
    }
}

