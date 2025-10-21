package com.pawcial.service

import com.pawcial.dto.SexDto
import com.pawcial.entity.dictionary.Sex
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SexService {

    fun findAll(): List<SexDto> {
        return Sex.findAll().list()
            .map { it.toDto() }
    }
}

