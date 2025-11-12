package com.pawcial.service

import com.pawcial.dto.TrainingLevelDto
import com.pawcial.dto.CreateTrainingLevelRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.TrainingLevel
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class TrainingLevelService {

    fun findAll(all: Boolean = false): List<TrainingLevelDto> {
        val query = if (all) {
            TrainingLevel.findAll()
        } else {
            TrainingLevel.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateTrainingLevelRequest): TrainingLevelDto {
        ValidationUtils.validateCode(request.code, "TrainingLevel code")

        val existing = TrainingLevel.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("TrainingLevel with code '${request.code}' already exists")
        }

        val trainingLevel = TrainingLevel().apply {
            code = request.code
            label = request.label
        }
        trainingLevel.persist()
        return trainingLevel.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): TrainingLevelDto {
        val trainingLevel = TrainingLevel.findById(code)
            ?: throw IllegalArgumentException("TrainingLevel with code '$code' not found")

        trainingLevel.label = request.label
        trainingLevel.persist()
        return trainingLevel.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val trainingLevel = TrainingLevel.findById(code) ?: return false
        trainingLevel.isActive = !trainingLevel.isActive
        trainingLevel.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val trainingLevel = TrainingLevel.findById(code) ?: return false
        trainingLevel.delete()
        return true
    }
}

