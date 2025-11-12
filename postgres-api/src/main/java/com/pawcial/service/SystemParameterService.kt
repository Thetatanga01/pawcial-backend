package com.pawcial.service

import com.pawcial.dto.CreateSystemParameterRequest
import com.pawcial.dto.SystemParameterDto
import com.pawcial.dto.UpdateSystemParameterRequest
import com.pawcial.entity.dictionary.SystemParameter
import com.pawcial.extension.toDto
import com.pawcial.util.ValidationUtils
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class SystemParameterService {

    companion object {
        const val HARD_DELETE_WINDOW_SECONDS = "HARD_DELETE_WINDOW_SECONDS"
    }

    fun findAll(all: Boolean = false): List<SystemParameterDto> {
        val query = if (all) {
            SystemParameter.findAll()
        } else {
            SystemParameter.find("isActive", true)
        }
        return query.list()
            .sortedBy { it.label }
            .map { it.toDto() }
    }

    fun findByCode(code: String): SystemParameterDto {
        return SystemParameter.findById(code)?.toDto()
            ?: throw NotFoundException("SystemParameter not found: $code")
    }

    fun getHardDeleteWindowSeconds(): Long {
        val param = SystemParameter.findById(HARD_DELETE_WINDOW_SECONDS)
        return param?.parameterValue?.toLongOrNull() ?: 300L // Default 5 minutes
    }

    @Transactional
    fun create(request: CreateSystemParameterRequest): SystemParameterDto {
        ValidationUtils.validateCode(request.code, "SystemParameter code")

        val existing = SystemParameter.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("SystemParameter with code '${request.code}' already exists")
        }

        val parameter = SystemParameter().apply {
            code = request.code
            label = request.label
            parameterValue = request.parameterValue
            description = request.description
        }
        parameter.persist()
        return parameter.toDto()
    }

    @Transactional
    fun update(code: String, request: UpdateSystemParameterRequest): SystemParameterDto {
        val parameter = SystemParameter.findById(code)
            ?: throw NotFoundException("SystemParameter not found: $code")

        request.label?.let { parameter.label = it }
        request.parameterValue?.let { parameter.parameterValue = it }
        request.description?.let { parameter.description = it }

        parameter.persist()
        return parameter.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val parameter = SystemParameter.findById(code) ?: return false
        parameter.isActive = !parameter.isActive
        parameter.persist()
        return true
    }
}

