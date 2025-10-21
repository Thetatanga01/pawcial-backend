package com.pawcial.service

import com.pawcial.dto.ColorDto
import com.pawcial.dto.CreateColorRequest
import com.pawcial.entity.dictionary.Color
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class ColorService {

    fun findAll(): List<ColorDto> {
        return Color.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateColorRequest): ColorDto {
        val color = Color().apply {
            code = request.code
            label = request.label
        }
        color.persist()
        return color.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val color = Color.findById(code) ?: return false
        color.isActive = !color.isActive
        return true
    }
}
