package com.pawcial.service

import com.pawcial.dto.SizeDto
import com.pawcial.dto.CreateSizeRequest
import com.pawcial.entity.dictionary.Size
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class SizeService {

    fun findAll(all: Boolean = false): List<SizeDto> {
        return if (all) {
            Size.findAll().list()
        } else {
            Size.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateSizeRequest): SizeDto {
        val size = Size().apply {
            code = request.code
            label = request.label
        }
        size.persist()
        return size.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val size = Size.findById(code) ?: return false
        size.isActive = !size.isActive
        size.persist()
        return true
    }
}

