package com.pawcial.service

import com.pawcial.dto.TrainingLevelDto
import com.pawcial.entity.dictionary.TrainingLevel
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TrainingLevelService {

    fun findAll(): List<TrainingLevelDto> {
        return TrainingLevel.findAll().list()
            .map { it.toDto() }
    }
}

