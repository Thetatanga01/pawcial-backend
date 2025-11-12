package com.pawcial.service

import com.pawcial.dto.ZonePurposeDto
import com.pawcial.dto.CreateZonePurposeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.ZonePurpose
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class ZonePurposeService {

    fun findAll(all: Boolean = false): List<ZonePurposeDto> {
        val query = if (all) {
            ZonePurpose.findAll()
        } else {
            ZonePurpose.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateZonePurposeRequest): ZonePurposeDto {
        ValidationUtils.validateCode(request.code, "ZonePurpose code")

        val existing = ZonePurpose.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("ZonePurpose with code '${request.code}' already exists")
        }

        val zonePurpose = ZonePurpose().apply {
            code = request.code
            label = request.label
        }
        zonePurpose.persist()
        return zonePurpose.toDto()
    }

    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): ZonePurposeDto {
        val zonePurpose = ZonePurpose.findById(code)
            ?: throw IllegalArgumentException("ZonePurpose with code '$code' not found")

        zonePurpose.label = request.label
        zonePurpose.persist()
        return zonePurpose.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val zonePurpose = ZonePurpose.findById(code) ?: return false
        zonePurpose.isActive = !zonePurpose.isActive
        zonePurpose.persist()
        return true
    }

    @Transactional
    fun hardDelete(code: String): Boolean {
        val zonePurpose = ZonePurpose.findById(code) ?: return false
        zonePurpose.delete()
        return true
    }
}
