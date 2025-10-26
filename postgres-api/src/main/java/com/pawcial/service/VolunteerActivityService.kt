package com.pawcial.service

import com.pawcial.dto.CreateVolunteerActivityRequest
import com.pawcial.entity.core.Volunteer
import com.pawcial.entity.core.VolunteerActivity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class VolunteerActivityService {

    fun findAll(volunteerId: UUID?, all: Boolean = false): List<VolunteerActivity> {
        val activeFilter = if (all) "" else " and isActive = true"
        return if (volunteerId != null) {
            VolunteerActivity.find("volunteer.id = ?1$activeFilter", volunteerId).list()
        } else {
            if (all) {
                VolunteerActivity.findAll().list()
            } else {
                VolunteerActivity.find("isActive = true").list()
            }
        }
    }

    fun findById(id: UUID): VolunteerActivity {
        return VolunteerActivity.findById(id)
            ?: throw NotFoundException("VolunteerActivity not found: $id")
    }

    @Transactional
    fun create(request: CreateVolunteerActivityRequest): VolunteerActivity {
        val volunteer = Volunteer.findById(request.volunteerId)
            ?: throw NotFoundException("Volunteer not found: ${request.volunteerId}")

        val activity = VolunteerActivity().apply {
            this.volunteer = volunteer
            activityDate = request.activityDate
            areaCode = request.areaCode
            hours = request.hours
            description = request.description
        }
        activity.persist()
        return activity
    }

    @Transactional
    fun delete(id: UUID) {
        val activity = VolunteerActivity.findById(id)
            ?: throw NotFoundException("VolunteerActivity not found: $id")
        activity.isActive = false
        activity.persist()
    }
}

