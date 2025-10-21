package com.pawcial.resource

import com.pawcial.dto.VolunteerStatusDto
import com.pawcial.dto.CreateVolunteerStatusRequest
import com.pawcial.service.VolunteerStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

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

    @POST
    fun createVolunteerStatus(request: CreateVolunteerStatusRequest): Response {
        val created = volunteerStatusService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleVolunteerStatusActive(@PathParam("code") code: String): Response {
        val toggled = volunteerStatusService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

