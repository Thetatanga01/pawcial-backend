package com.pawcial.service

import com.pawcial.dto.VolunteerAreaDto
import com.pawcial.entity.dictionary.VolunteerAreaDictionary
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class VolunteerAreaService {

    fun findAll(): List<VolunteerAreaDto> {
        return VolunteerAreaDictionary.findAll().list()
            .map { it.toDto() }
    }
}

