package com.pawcial.service

import com.pawcial.dto.ServiceTypeDto
import com.pawcial.entity.dictionary.ServiceType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ServiceTypeService {

    fun findAll(): List<ServiceTypeDto> {
        return ServiceType.findAll().list()
            .map { it.toDto() }
    }
}

