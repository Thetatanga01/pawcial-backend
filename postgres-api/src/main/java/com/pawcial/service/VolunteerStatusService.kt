package com.pawcial.service

import com.pawcial.dto.VolunteerStatusDto
import com.pawcial.entity.dictionary.VolunteerStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class VolunteerStatusService {

    fun findAll(): List<VolunteerStatusDto> {
        return VolunteerStatus.findAll().list()
            .map { it.toDto() }
    }
}

