package com.pawcial.service

import com.pawcial.dto.ZonePurposeDto
import com.pawcial.dto.CreateZonePurposeRequest
import com.pawcial.entity.dictionary.ZonePurpose
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class ZonePurposeService {

    fun findAll(): List<ZonePurposeDto> {
        return ZonePurpose.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateZonePurposeRequest): ZonePurposeDto {
        val zonePurpose = ZonePurpose().apply {
            code = request.code
            label = request.label
        }
        zonePurpose.persist()
        return zonePurpose.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val zonePurpose = ZonePurpose.findById(code) ?: return false
        zonePurpose.isActive = !zonePurpose.isActive
        return true
    }
}

