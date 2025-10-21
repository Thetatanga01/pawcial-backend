package com.pawcial.service

import com.pawcial.dto.VaccineDto
import com.pawcial.dto.CreateVaccineRequest
import com.pawcial.entity.dictionary.Vaccine
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class VaccineService {

    fun findAll(all: Boolean = false): List<VaccineDto> {
        return if (all) {
            Vaccine.findAll().list()
        } else {
            Vaccine.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateVaccineRequest): VaccineDto {
        val vaccine = Vaccine().apply {
            code = request.code
            label = request.label
        }
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
}

