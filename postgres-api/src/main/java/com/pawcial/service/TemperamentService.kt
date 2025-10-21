package com.pawcial.service

import com.pawcial.dto.TemperamentDto
import com.pawcial.entity.dictionary.Temperament
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TemperamentService {

    fun findAll(): List<TemperamentDto> {
        return Temperament.findAll().list()
            .map { it.toDto() }
    }
}

