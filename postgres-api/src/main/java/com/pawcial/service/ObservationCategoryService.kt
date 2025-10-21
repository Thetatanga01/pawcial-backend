package com.pawcial.service

import com.pawcial.dto.ObservationCategoryDto
import com.pawcial.dto.CreateObservationCategoryRequest
import com.pawcial.entity.dictionary.ObservationCategory
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class ObservationCategoryService {

    fun findAll(all: Boolean = false): List<ObservationCategoryDto> {
        return if (all) {
            ObservationCategory.findAll().list()
        } else {
            ObservationCategory.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateObservationCategoryRequest): ObservationCategoryDto {
        val observationCategory = ObservationCategory().apply {
            code = request.code
            label = request.label
        }
        observationCategory.persist()
        return observationCategory.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val observationCategory = ObservationCategory.findById(code) ?: return false
        observationCategory.isActive = !observationCategory.isActive
        observationCategory.persist()
        return true
    }
}

