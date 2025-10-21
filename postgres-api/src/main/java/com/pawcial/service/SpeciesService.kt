package com.pawcial.service

import com.pawcial.dto.SpeciesDto
import com.pawcial.entity.core.Species
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SpeciesService {

    fun findAll(): List<SpeciesDto> {
        return Species.findAll().list()
            .map { it.toDto() }
    }
}