package com.pawcial.service

import com.pawcial.dto.DoseRouteDto
import com.pawcial.dto.CreateDoseRouteRequest
import com.pawcial.entity.dictionary.DoseRoute
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class DoseRouteService {

    fun findAll(): List<DoseRouteDto> {
        return DoseRoute.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateDoseRouteRequest): DoseRouteDto {
        val doseRoute = DoseRoute().apply {
            code = request.code
            label = request.label
        }
        doseRoute.persist()
        return doseRoute.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val doseRoute = DoseRoute.findById(code) ?: return false
        doseRoute.isActive = !doseRoute.isActive
        return true
    }
}

