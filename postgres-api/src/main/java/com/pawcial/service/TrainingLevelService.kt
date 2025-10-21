package com.pawcial.service

import com.pawcial.dto.TrainingLevelDto
import com.pawcial.dto.CreateTrainingLevelRequest
import com.pawcial.entity.dictionary.TrainingLevel
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class TrainingLevelService {

    fun findAll(): List<TrainingLevelDto> {
        return TrainingLevel.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateTrainingLevelRequest): TrainingLevelDto {
        val trainingLevel = TrainingLevel().apply {
            code = request.code
            label = request.label
        }
        trainingLevel.persist()
        return trainingLevel.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val trainingLevel = TrainingLevel.findById(code) ?: return false
        trainingLevel.isActive = !trainingLevel.isActive
        return true
    }
}

