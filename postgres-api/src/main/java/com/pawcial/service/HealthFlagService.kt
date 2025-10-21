package com.pawcial.service

import com.pawcial.dto.HealthFlagDto
import com.pawcial.entity.dictionary.HealthFlag
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class HealthFlagService {

    fun findAll(): List<HealthFlagDto> {
        return HealthFlag.findAll().list()
            .map { it.toDto() }
    }
}

