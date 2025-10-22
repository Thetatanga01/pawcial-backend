package com.pawcial.resource

import com.pawcial.dto.VolunteerAreaDto
import com.pawcial.dto.CreateVolunteerAreaDictionaryRequest
import com.pawcial.service.VolunteerAreaService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/volunteer-areas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class VolunteerAreaResource {

    @Inject
    lateinit var volunteerAreaService: VolunteerAreaService

    @GET
    fun getAllVolunteerAreas(@QueryParam("all") @DefaultValue("false") all: Boolean): List<VolunteerAreaDto> {
        return volunteerAreaService.findAll(all)
    }

    @POST
    fun createVolunteerArea(request: CreateVolunteerAreaDictionaryRequest): Response {
        val created = volunteerAreaService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleVolunteerAreaActive(@PathParam("code") code: String): Response {
        val toggled = volunteerAreaService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

