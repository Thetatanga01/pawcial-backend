package com.pawcial.resource

import com.pawcial.dto.VolunteerStatusDto
import com.pawcial.service.VolunteerStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/volunteer-statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class VolunteerStatusResource {

    @Inject
    lateinit var volunteerStatusService: VolunteerStatusService

    @GET
    fun getAllVolunteerStatuses(): List<VolunteerStatusDto> {
        return volunteerStatusService.findAll()
    }
}

