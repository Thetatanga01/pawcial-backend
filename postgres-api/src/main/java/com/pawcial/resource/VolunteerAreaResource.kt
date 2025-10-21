package com.pawcial.resource

import com.pawcial.dto.VolunteerAreaDto
import com.pawcial.service.VolunteerAreaService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/volunteer-areas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class VolunteerAreaResource {

    @Inject
    lateinit var volunteerAreaService: VolunteerAreaService

    @GET
    fun getAllVolunteerAreas(): List<VolunteerAreaDto> {
        return volunteerAreaService.findAll()
    }
}

