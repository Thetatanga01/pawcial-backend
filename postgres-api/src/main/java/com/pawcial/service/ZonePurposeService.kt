package com.pawcial.service

import com.pawcial.dto.ZonePurposeDto
import com.pawcial.entity.dictionary.ZonePurpose
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ZonePurposeService {

    fun findAll(): List<ZonePurposeDto> {
        return ZonePurpose.findAll().list()
            .map { it.toDto() }
    }
}

