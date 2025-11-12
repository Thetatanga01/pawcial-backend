package com.pawcial.service

import com.pawcial.dto.LeashBehaviorDto
import com.pawcial.dto.CreateLeashBehaviorRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.LeashBehavior
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class LeashBehaviorService {

    fun findAll(all: Boolean = false): List<LeashBehaviorDto> {
        val query = if (all) {
            LeashBehavior.findAll()
        } else {
            LeashBehavior.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateLeashBehaviorRequest): LeashBehaviorDto {
        // Validate code has no spaces
        ValidationUtils.validateCode(request.code, "Leash behavior code")

        // Check if leash behavior with this code already exists
        val existing = LeashBehavior.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("Leash behavior with code '${request.code}' already exists")
        }

        val leashBehavior = LeashBehavior().apply {
            code = request.code
            label = request.label
        }
        leashBehavior.persist()
        return leashBehavior.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): LeashBehaviorDto {
        val leashBehavior = LeashBehavior.findById(code)
            ?: throw IllegalArgumentException("Leash behavior with code '$code' not found")

        leashBehavior.label = request.label
        leashBehavior.persist()
        return leashBehavior.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val leashBehavior = LeashBehavior.findById(code) ?: return false
        leashBehavior.isActive = !leashBehavior.isActive
        leashBehavior.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val leashBehavior = LeashBehavior.findById(code) ?: return false
        leashBehavior.delete()
        return true
    }
}

