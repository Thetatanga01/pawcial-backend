package com.pawcial.service

import com.pawcial.dto.PlacementStatusDto
import com.pawcial.entity.dictionary.PlacementStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PlacementStatusService {

    fun findAll(): List<PlacementStatusDto> {
        return PlacementStatus.findAll().list()
            .map { it.toDto() }
    }
}

