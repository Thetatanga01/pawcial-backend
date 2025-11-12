package com.pawcial.service

import com.pawcial.dto.OutcomeTypeDto
import com.pawcial.dto.CreateOutcomeTypeRequest
import com.pawcial.entity.dictionary.OutcomeType
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class OutcomeTypeService {

    fun findAll(all: Boolean = false): List<OutcomeTypeDto> {
        val query = if (all) {
            OutcomeType.findAll()
        } else {
            OutcomeType.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateOutcomeTypeRequest): OutcomeTypeDto {
        ValidationUtils.validateCode(request.code, "OutcomeType code")

        val outcomeType = OutcomeType().apply {
            code = request.code
            label = request.label
        }
        outcomeType.persist()
        return outcomeType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val outcomeType = OutcomeType.findById(code) ?: return false
        outcomeType.isActive = !outcomeType.isActive
        outcomeType.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val outcomeType = OutcomeType.findById(code) ?: return false
        outcomeType.delete()
        return true
    }
}

