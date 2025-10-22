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

    fun findAll(volunteerId: UUID?): List<VolunteerActivity> {
        return if (volunteerId != null) {
            VolunteerActivity.find("volunteer.id", volunteerId).list()
        } else {
            VolunteerActivity.findAll().list()
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
        val deleted = VolunteerActivity.deleteById(id)
        if (!deleted) {
            throw NotFoundException("VolunteerActivity not found: $id")
        }
    }
}

