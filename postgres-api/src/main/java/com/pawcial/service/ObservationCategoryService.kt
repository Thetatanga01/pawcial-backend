package com.pawcial.service

import com.pawcial.dto.ObservationCategoryDto
import com.pawcial.entity.dictionary.ObservationCategory
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ObservationCategoryService {

    fun findAll(): List<ObservationCategoryDto> {
        return ObservationCategory.findAll().list()
            .map { it.toDto() }
    }
}

