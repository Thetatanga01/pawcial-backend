package com.pawcial.service

import com.pawcial.dto.SizeDto
import com.pawcial.dto.CreateSizeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.Size
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class SizeService {

    fun findAll(all: Boolean = false): List<SizeDto> {
        val query = if (all) {
            Size.findAll()
        } else {
            Size.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateSizeRequest): SizeDto {
        ValidationUtils.validateCode(request.code, "Size code")

        val existing = Size.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("Size with code '${request.code}' already exists")
        }

        val size = Size().apply {
            code = request.code
            label = request.label
        }
        size.persist()
        return size.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): SizeDto {
        val size = Size.findById(code)
            ?: throw IllegalArgumentException("Size with code '$code' not found")

        size.label = request.label
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

    @Transactional
    fun hardDelete(code: String): Boolean {
        val size = Size.findById(code) ?: return false
        size.delete()
        return true
    }
}

