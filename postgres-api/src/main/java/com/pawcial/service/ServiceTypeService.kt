package com.pawcial.service

import com.pawcial.dto.ServiceTypeDto
import com.pawcial.dto.CreateServiceTypeRequest
import com.pawcial.entity.dictionary.ServiceType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class ServiceTypeService {

    fun findAll(): List<ServiceTypeDto> {
        return ServiceType.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateServiceTypeRequest): ServiceTypeDto {
        val serviceType = ServiceType().apply {
            code = request.code
            label = request.label
        }
        serviceType.persist()
        return serviceType.toDto()
    }

    @Transactional
    fun toggleActive(code: String): Boolean {
        val serviceType = ServiceType.findById(code) ?: return false
        serviceType.isActive = !serviceType.isActive
        return true
    }
}

