package com.pawcial.service

import com.pawcial.dto.DomesticStatusDto
import com.pawcial.dto.CreateDomesticStatusRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.DomesticStatus
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class DomesticStatusService {

    fun findAll(all: Boolean = false): List<DomesticStatusDto> {
        val query = if (all) {
            DomesticStatus.findAll()
        } else {
            DomesticStatus.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateDomesticStatusRequest): DomesticStatusDto {
        ValidationUtils.validateCode(request.code, "DomesticStatus code")

        val existing = DomesticStatus.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("DomesticStatus with code '${request.code}' already exists")
        }

        val domesticStatus = DomesticStatus().apply {
            code = request.code
            label = request.label
        }
        domesticStatus.persist()
        return domesticStatus.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): DomesticStatusDto {
        val domesticStatus = DomesticStatus.findById(code)
            ?: throw IllegalArgumentException("DomesticStatus with code '$code' not found")

        domesticStatus.label = request.label
        domesticStatus.persist()
        return domesticStatus.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val domesticStatus = DomesticStatus.findById(code) ?: return false
        domesticStatus.isActive = !domesticStatus.isActive
        domesticStatus.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val domesticStatus = DomesticStatus.findById(code) ?: return false
        domesticStatus.delete()
        return true
    }
}

