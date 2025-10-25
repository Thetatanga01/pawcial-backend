package com.pawcial.service

import com.pawcial.dto.ObservationCategoryDto
import com.pawcial.dto.CreateObservationCategoryRequest
import com.pawcial.dto.UpdateLabelRequest
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
        val existing = ObservationCategory.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("ObservationCategory with code '${request.code}' already exists")
        }

        val observationCategory = ObservationCategory().apply {
            code = request.code
            label = request.label
        }
        observationCategory.persist()
        return observationCategory.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): ObservationCategoryDto {
        val observationCategory = ObservationCategory.findById(code)
            ?: throw IllegalArgumentException("ObservationCategory with code '$code' not found")

        observationCategory.label = request.label
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
