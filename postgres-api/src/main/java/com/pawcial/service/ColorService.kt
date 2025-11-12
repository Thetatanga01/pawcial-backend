package com.pawcial.service

import com.pawcial.dto.ColorDto
import com.pawcial.dto.CreateColorRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.Color
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class ColorService {

    fun findAll(all: Boolean = false): List<ColorDto> {
        val query = if (all) {
            Color.findAll()
        } else {
            Color.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateColorRequest): ColorDto {
        // Validate code has no spaces
        ValidationUtils.validateCode(request.code, "Color code")

        // Check if color with this code already exists
        val existing = Color.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("Color with code '${request.code}' already exists")
        }

        val color = Color().apply {
            code = request.code
            label = request.label
        }
        color.persist()
        return color.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): ColorDto {
        val color = Color.findById(code)
            ?: throw IllegalArgumentException("Color with code '$code' not found")

        color.label = request.label
        color.persist()
        return color.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val color = Color.findById(code) ?: return false
        color.isActive = !color.isActive
        color.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val color = Color.findById(code) ?: return false
        color.delete()
        return true
    }
}
