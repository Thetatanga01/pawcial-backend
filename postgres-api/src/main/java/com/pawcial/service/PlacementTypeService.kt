package com.pawcial.service

import com.pawcial.dto.PlacementTypeDto
import com.pawcial.entity.dictionary.PlacementType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PlacementTypeService {

    fun findAll(): List<PlacementTypeDto> {
        return PlacementType.findAll().list()
            .map { it.toDto() }
    }
}

