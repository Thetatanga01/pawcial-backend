package com.pawcial.service

import com.pawcial.dto.SexDto
import com.pawcial.dto.CreateSexRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.Sex
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class SexService {

    fun findAll(all: Boolean = false): List<SexDto> {
        return if (all) {
            Sex.findAll().list()
        } else {
            Sex.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateSexRequest): SexDto {
        val existing = Sex.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("Sex with code '${request.code}' already exists")
        }

        val sex = Sex().apply {
            code = request.code
            label = request.label
        }
        sex.persist()
        return sex.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): SexDto {
        val sex = Sex.findById(code)
            ?: throw IllegalArgumentException("Sex with code '$code' not found")

        sex.label = request.label
        sex.persist()
        return sex.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val sex = Sex.findById(code) ?: return false
        sex.isActive = !sex.isActive
        sex.persist()
        return true
    }
}
