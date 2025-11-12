package com.pawcial.service

import com.pawcial.dto.HealthFlagDto
import com.pawcial.dto.CreateHealthFlagRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.HealthFlag
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class HealthFlagService {

    fun findAll(all: Boolean = false): List<HealthFlagDto> {
        val query = if (all) {
            HealthFlag.findAll()
        } else {
            HealthFlag.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateHealthFlagRequest): HealthFlagDto {
        ValidationUtils.validateCode(request.code, "HealthFlag code")

        val existing = HealthFlag.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("HealthFlag with code '${request.code}' already exists")
        }

        val healthFlag = HealthFlag().apply {
            code = request.code
            label = request.label
        }
        healthFlag.persist()
        return healthFlag.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): HealthFlagDto {
        val healthFlag = HealthFlag.findById(code)
            ?: throw IllegalArgumentException("HealthFlag with code '$code' not found")

        healthFlag.label = request.label
        healthFlag.persist()
        return healthFlag.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val healthFlag = HealthFlag.findById(code) ?: return false
        healthFlag.isActive = !healthFlag.isActive
        healthFlag.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val healthFlag = HealthFlag.findById(code) ?: return false
        healthFlag.delete()
        return true
    }
}

