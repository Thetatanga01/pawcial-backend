package com.pawcial.service

import com.pawcial.dto.ColorDto
import com.pawcial.entity.dictionary.Color
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ColorService {

    fun findAll(): List<ColorDto> {
        return Color.findAll().list()
            .map { it.toDto() }
    }
}
