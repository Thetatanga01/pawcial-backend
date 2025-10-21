package com.pawcial.service

import com.pawcial.dto.DoseRouteDto
import com.pawcial.entity.dictionary.DoseRoute
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class DoseRouteService {

    fun findAll(): List<DoseRouteDto> {
        return DoseRoute.findAll().list()
            .map { it.toDto() }
    }
}

