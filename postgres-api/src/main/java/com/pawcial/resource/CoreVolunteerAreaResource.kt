package com.pawcial.resource

import com.pawcial.dto.CreateVolunteerAreaRequest
import com.pawcial.service.CoreVolunteerAreaService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/core-volunteer-areas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class CoreVolunteerAreaResource {

    @Inject
    lateinit var coreVolunteerAreaService: CoreVolunteerAreaService

    @POST
    fun createVolunteerArea(request: CreateVolunteerAreaRequest): Response {
        val created = coreVolunteerAreaService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}


