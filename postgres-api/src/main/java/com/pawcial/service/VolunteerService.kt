package com.pawcial.service

import com.pawcial.dto.CreateVolunteerRequest
import com.pawcial.dto.UpdateVolunteerRequest
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

    fun findAll(all: Boolean = false): List<VolunteerDto> {
        return if (all) {
            Volunteer.findAll().list().map { it.toDto() }
        } else {
            Volunteer.find("isActive = true").list().map { it.toDto() }
        }
    }

    fun findById(id: UUID): VolunteerDto {
        return Volunteer.findById(id)?.toDto()
            ?: throw NotFoundException("Volunteer not found: $id")
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

    @Transactional
    fun update(id: UUID, request: UpdateVolunteerRequest): VolunteerDto {
        val volunteer = Volunteer.findById(id)
            ?: throw NotFoundException("Volunteer not found: $id")

        request.personId?.let {
            volunteer.person = Person.findById(it)
                ?: throw NotFoundException("Person not found: $it")
        }

        volunteer.apply {
            request.status?.let { status = it }
            request.startDate?.let { startDate = it }
            request.endDate?.let { endDate = it }
            request.volunteerCode?.let { volunteerCode = it }
            request.notes?.let { notes = it }
        }
        volunteer.persist()
        return volunteer.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val volunteer = Volunteer.findById(id)
            ?: throw NotFoundException("Volunteer not found: $id")
        volunteer.isActive = false
        volunteer.persist()
    }
}
