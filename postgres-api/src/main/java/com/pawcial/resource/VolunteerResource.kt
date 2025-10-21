package com.pawcial.resource

import com.pawcial.dto.VolunteerDto
import com.pawcial.service.VolunteerService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/volunteers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class VolunteerResource {

    @Inject
    lateinit var volunteerService: VolunteerService

    @GET
    fun getAllVolunteers(): List<VolunteerDto> {
        return volunteerService.findAll()
    }
}

