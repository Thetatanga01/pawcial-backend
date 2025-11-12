package com.pawcial.service

import com.pawcial.dto.TemperamentDto
import com.pawcial.dto.CreateTemperamentRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.Temperament
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class TemperamentService {

    fun findAll(all: Boolean = false): List<TemperamentDto> {
        val query = if (all) {
            Temperament.findAll()
        } else {
            Temperament.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateTemperamentRequest): TemperamentDto {
        ValidationUtils.validateCode(request.code, "Temperament code")

        val existing = Temperament.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("Temperament with code '${request.code}' already exists")
        }

        val temperament = Temperament().apply {
            code = request.code
            label = request.label
        }
        temperament.persist()
        return temperament.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): TemperamentDto {
        val temperament = Temperament.findById(code)
            ?: throw IllegalArgumentException("Temperament with code '$code' not found")

        temperament.label = request.label
        temperament.persist()
        return temperament.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val temperament = Temperament.findById(code) ?: return false
        temperament.isActive = !temperament.isActive
        temperament.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val temperament = Temperament.findById(code) ?: return false
        temperament.delete()
        return true
    }
}

