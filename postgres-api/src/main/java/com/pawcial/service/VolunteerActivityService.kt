package com.pawcial.service

import com.pawcial.dto.CreateVolunteerActivityRequest
import com.pawcial.entity.core.Volunteer
import com.pawcial.entity.core.VolunteerActivity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class VolunteerActivityService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

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
        activity.isActive = !activity.isActive
        activity.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        val activity = VolunteerActivity.findById(id)
            ?: throw NotFoundException("VolunteerActivity not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = activity.createdAt ?: throw IllegalStateException("VolunteerActivity has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        activity.delete()
    }
}
