package com.pawcial.service

import com.pawcial.dto.SizeDto
import com.pawcial.entity.dictionary.Size
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SizeService {

    fun findAll(): List<SizeDto> {
        return Size.findAll().list()
            .map { it.toDto() }
    }
}

