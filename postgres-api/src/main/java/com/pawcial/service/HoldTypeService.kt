package com.pawcial.service

import com.pawcial.dto.HoldTypeDto
import com.pawcial.dto.CreateHoldTypeRequest
import com.pawcial.entity.dictionary.HoldType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class HoldTypeService {

    fun findAll(all: Boolean = false): List<HoldTypeDto> {
        return if (all) {
            HoldType.findAll().list()
        } else {
            HoldType.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateHoldTypeRequest): HoldTypeDto {
        val holdType = HoldType().apply {
            code = request.code
            label = request.label
        }
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
}

