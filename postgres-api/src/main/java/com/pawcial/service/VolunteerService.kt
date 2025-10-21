package com.pawcial.service

import com.pawcial.dto.VolunteerDto
import com.pawcial.entity.core.Volunteer
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class VolunteerService {

    fun findAll(): List<VolunteerDto> {
        return Volunteer.findAll().list()
            .map { it.toDto() }
    }
}

