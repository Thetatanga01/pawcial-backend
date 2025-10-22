package com.pawcial.service

import com.pawcial.dto.CreateVolunteerAreaRequest
import com.pawcial.entity.core.Volunteer
import com.pawcial.entity.core.VolunteerArea
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class CoreVolunteerAreaService {

    @Transactional
    fun create(request: CreateVolunteerAreaRequest): VolunteerArea {
        val volunteer = Volunteer.findById(request.volunteerId)
            ?: throw NotFoundException("Volunteer not found: ${request.volunteerId}")

        val volunteerArea = VolunteerArea().apply {
            this.volunteer = volunteer
            areaCode = request.areaCode
            proficiencyLevel = request.proficiencyLevel
            notes = request.notes
        }
        volunteerArea.persist()
        return volunteerArea
    }
}