package com.pawcial.service

import com.pawcial.dto.SourceTypeDto
import com.pawcial.dto.CreateSourceTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.SourceType
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class SourceTypeService {

    fun findAll(all: Boolean = false): List<SourceTypeDto> {
        val query = if (all) {
            SourceType.findAll()
        } else {
            SourceType.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateSourceTypeRequest): SourceTypeDto {
        ValidationUtils.validateCode(request.code, "SourceType code")

        val existing = SourceType.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("SourceType with code '${request.code}' already exists")
        }

        val sourceType = SourceType().apply {
            code = request.code
            label = request.label
        }
        sourceType.persist()
        return sourceType.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): SourceTypeDto {
        val sourceType = SourceType.findById(code)
            ?: throw IllegalArgumentException("SourceType with code '$code' not found")

        sourceType.label = request.label
        sourceType.persist()
        return sourceType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val sourceType = SourceType.findById(code) ?: return false
        sourceType.isActive = !sourceType.isActive
        sourceType.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val sourceType = SourceType.findById(code) ?: return false
        sourceType.delete()
        return true
    }
}
