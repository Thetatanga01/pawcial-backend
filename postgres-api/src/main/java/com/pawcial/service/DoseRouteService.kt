package com.pawcial.service

import com.pawcial.dto.DoseRouteDto
import com.pawcial.dto.CreateDoseRouteRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.DoseRoute
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class DoseRouteService {

    fun findAll(all: Boolean = false): List<DoseRouteDto> {
        return if (all) {
            DoseRoute.findAll().list()
        } else {
            DoseRoute.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateDoseRouteRequest): DoseRouteDto {
        val existing = DoseRoute.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("DoseRoute with code '${request.code}' already exists")
        }

        val doseRoute = DoseRoute().apply {
            code = request.code
            label = request.label
        }
        doseRoute.persist()
        return doseRoute.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): DoseRouteDto {
        val doseRoute = DoseRoute.findById(code)
            ?: throw IllegalArgumentException("DoseRoute with code '$code' not found")

        doseRoute.label = request.label
        doseRoute.persist()
        return doseRoute.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val doseRoute = DoseRoute.findById(code) ?: return false
        doseRoute.isActive = !doseRoute.isActive
        doseRoute.persist()
        return true
    }
}
