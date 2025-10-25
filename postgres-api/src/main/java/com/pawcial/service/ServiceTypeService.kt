package com.pawcial.service

import com.pawcial.dto.ServiceTypeDto
import com.pawcial.dto.CreateServiceTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.entity.dictionary.ServiceType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class ServiceTypeService {

    fun findAll(all: Boolean = false): List<ServiceTypeDto> {
        return if (all) {
            ServiceType.findAll().list()
        } else {
            ServiceType.find("isActive", true).list()
        }.map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateServiceTypeRequest): ServiceTypeDto {
        val existing = ServiceType.findById(request.code)
        if (existing != null) {
            throw IllegalArgumentException("ServiceType with code '${request.code}' already exists")
        }

        val serviceType = ServiceType().apply {
            code = request.code
            label = request.label
        }
        serviceType.persist()
        return serviceType.toDto()
    }
    @Transactional
    fun updateLabel(code: String, request: UpdateLabelRequest): ServiceTypeDto {
        val serviceType = ServiceType.findById(code)
            ?: throw IllegalArgumentException("ServiceType with code '$code' not found")

        serviceType.label = request.label
        serviceType.persist()
        return serviceType.toDto()
    }


    @Transactional
    fun toggleActive(code: String): Boolean {
        val serviceType = ServiceType.findById(code) ?: return false
        serviceType.isActive = !serviceType.isActive
        serviceType.persist()
        return true
    }
}

