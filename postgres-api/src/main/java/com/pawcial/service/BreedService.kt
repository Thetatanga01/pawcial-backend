package com.pawcial.service

import com.pawcial.dto.BreedDto
import com.pawcial.entity.core.Breed
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class BreedService {

    fun findAll(): List<BreedDto> {
        return Breed.findAll().list()
            .map { it.toDto() }
    }
}

