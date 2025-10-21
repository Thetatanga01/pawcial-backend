package com.pawcial.service

import com.pawcial.dto.DomesticStatusDto
import com.pawcial.entity.dictionary.DomesticStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class DomesticStatusService {

    fun findAll(): List<DomesticStatusDto> {
        return DomesticStatus.findAll().list()
            .map { it.toDto() }
    }
}

