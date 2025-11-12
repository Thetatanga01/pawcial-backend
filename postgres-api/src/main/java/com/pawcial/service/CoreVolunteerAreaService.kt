package com.pawcial.service

import com.pawcial.dto.CreateVolunteerAreaRequest
import com.pawcial.entity.core.Volunteer
import com.pawcial.entity.core.VolunteerArea
import com.pawcial.entity.core.data.VolunteerAreaId
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class CoreVolunteerAreaService {

    fun findAll(volunteerId: UUID?, all: Boolean = false): List<VolunteerArea> {
        val activeFilter = if (all) "" else " and isActive = true"
        return if (volunteerId != null) {
            VolunteerArea.find("volunteer.id = ?1$activeFilter", volunteerId).list()
        } else {
            if (all) {
                VolunteerArea.findAll().list()
            } else {
                VolunteerArea.find("isActive = true").list()
            }
        }
    }

    fun findById(volunteerId: UUID, areaCode: String): VolunteerArea {
        return VolunteerArea.findById(VolunteerAreaId(volunteerId, areaCode))
            ?: throw NotFoundException("VolunteerArea not found for volunteer: $volunteerId and area: $areaCode")
    }

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

    @Transactional
    fun delete(volunteerId: UUID, areaCode: String) {
        val id = VolunteerAreaId(volunteerId, areaCode)
        val volunteerArea = VolunteerArea.findById(id)
            ?: throw NotFoundException("VolunteerArea not found for volunteer: $volunteerId and area: $areaCode")
        volunteerArea.isActive = !volunteerArea.isActive
        volunteerArea.persist()
    }
}