package com.pawcial.service

import com.pawcial.dto.VaccineDto
import com.pawcial.dto.CreateVaccineRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.Vaccine
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class VaccineService {

    fun findAll(all: Boolean = false): List<VaccineDto> {
        val query = if (all) {
            Vaccine.findAll()
        } else {
            Vaccine.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateVaccineRequest): VaccineDto {
        ValidationUtils.validateCode(request.code, "Vaccine code")

        val existing = Vaccine.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("Vaccine with code '${request.code}' already exists")
        }

        val vaccine = Vaccine().apply {
            code = request.code
            label = request.label
        }
        vaccine.persist()
        return vaccine.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): VaccineDto {
        val vaccine = Vaccine.findById(code)
            ?: throw IllegalArgumentException("Vaccine with code '$code' not found")

        vaccine.label = request.label
        vaccine.persist()
        return vaccine.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val vaccine = Vaccine.findById(code) ?: return false
        vaccine.isActive = !vaccine.isActive
        vaccine.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val vaccine = Vaccine.findById(code) ?: return false
        vaccine.delete()
        return true
    }
}
