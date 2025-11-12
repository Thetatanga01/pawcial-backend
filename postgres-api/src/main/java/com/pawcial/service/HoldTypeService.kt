package com.pawcial.service

import com.pawcial.dto.HoldTypeDto
import com.pawcial.dto.CreateHoldTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.HoldType
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class HoldTypeService {

    fun findAll(all: Boolean = false): List<HoldTypeDto> {
        val query = if (all) {
            HoldType.findAll()
        } else {
            HoldType.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateHoldTypeRequest): HoldTypeDto {
        ValidationUtils.validateCode(request.code, "HoldType code")

        val existing = HoldType.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("HoldType with code '${request.code}' already exists")
        }

        val holdType = HoldType().apply {
            code = request.code
            label = request.label
        }
        holdType.persist()
        return holdType.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): HoldTypeDto {
        val holdType = HoldType.findById(code)
            ?: throw IllegalArgumentException("HoldType with code '$code' not found")

        holdType.label = request.label
        holdType.persist()
        return holdType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val holdType = HoldType.findById(code) ?: return false
        holdType.isActive = !holdType.isActive
        holdType.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val holdType = HoldType.findById(code) ?: return false
        holdType.delete()
        return true
    }
}

