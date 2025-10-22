package com.pawcial.service

import com.pawcial.dto.CreateVolunteerActivityRequest
import com.pawcial.entity.core.Volunteer
import com.pawcial.entity.core.VolunteerActivity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class VolunteerActivityService {

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
}

