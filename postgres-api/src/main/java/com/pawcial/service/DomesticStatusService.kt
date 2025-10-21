package com.pawcial.service

import com.pawcial.dto.DomesticStatusDto
import com.pawcial.dto.CreateDomesticStatusRequest
import com.pawcial.entity.dictionary.DomesticStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class DomesticStatusService {

    fun findAll(): List<DomesticStatusDto> {
        return DomesticStatus.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateDomesticStatusRequest): DomesticStatusDto {
        val domesticStatus = DomesticStatus().apply {
            code = request.code
            label = request.label
        }
        domesticStatus.persist()
        return domesticStatus.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val domesticStatus = DomesticStatus.findById(code) ?: return false
        domesticStatus.isActive = !domesticStatus.isActive
        return true
    }
}

