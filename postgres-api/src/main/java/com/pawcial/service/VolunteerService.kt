package com.pawcial.service

import com.pawcial.dto.CreateVolunteerRequest
import com.pawcial.dto.PagedResponse
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

    fun findAll(all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<VolunteerDto> {
        val query = if (all) "1=1" else "isActive = true"

        // Get total count
        val totalElements = Volunteer.count(query)

        // Get paginated results
        val content = if (all) {
            Volunteer.findAll().page(page, size).list().map { it.toDto() }
        } else {
            Volunteer.find("isActive = true").page(page, size).list().map { it.toDto() }
        }

        val totalPages = ((totalElements + size - 1) / size).toInt()

        return PagedResponse(
            content = content,
            page = page,
            size = size,
            totalElements = totalElements,
            totalPages = totalPages,
            hasNext = page < totalPages - 1,
            hasPrevious = page > 0
        )
    }

    fun search(personName: String?, status: String?, volunteerCode: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<VolunteerDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (!personName.isNullOrBlank()) {
            query += " and lower(person.fullName) like lower(:personName)"
            params["personName"] = "%$personName%"
        }

        if (!status.isNullOrBlank()) {
            query += " and lower(status) like lower(:status)"
            params["status"] = "%$status%"
        }

        if (!volunteerCode.isNullOrBlank()) {
            query += " and lower(volunteerCode) like lower(:volunteerCode)"
            params["volunteerCode"] = "%$volunteerCode%"
        }

        // Get total count
        val totalElements = Volunteer.count(query, params)

        // Get paginated results
        val content = Volunteer.find(query, params)
            .page(page, size)
            .list()
            .map { it.toDto() }

        val totalPages = ((totalElements + size - 1) / size).toInt()

        return PagedResponse(
            content = content,
            page = page,
            size = size,
            totalElements = totalElements,
            totalPages = totalPages,
            hasNext = page < totalPages - 1,
            hasPrevious = page > 0
        )
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
        volunteer.isActive = !volunteer.isActive
        volunteer.persist()
    }
}
