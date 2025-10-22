package com.pawcial.service

import com.pawcial.dto.CreateVolunteerRequest
import com.pawcial.dto.VolunteerDto
import com.pawcial.entity.core.Person
import com.pawcial.entity.core.Volunteer
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class VolunteerService {

    fun findAll(): List<VolunteerDto> {
        return Volunteer.findAll().list()
            .map { it.toDto() }
    }

    @Transactional
    fun create(request: CreateVolunteerRequest): VolunteerDto {
        val person = Person.findById(request.personId)
            ?: throw NotFoundException("Person not found: ${request.personId}")

        val volunteer = Volunteer().apply {
            this.person = person
            status = request.status
            startDate = request.startDate
            endDate = request.endDate
            volunteerCode = request.volunteerCode
            notes = request.notes
        }
        volunteer.persist()
        return volunteer.toDto()
    }
}

