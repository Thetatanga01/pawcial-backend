package com.pawcial.service

import com.pawcial.dto.VaccineDto
import com.pawcial.entity.dictionary.Vaccine
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class VaccineService {

    fun findAll(): List<VaccineDto> {
        return Vaccine.findAll().list()
            .map { it.toDto() }
    }
}

