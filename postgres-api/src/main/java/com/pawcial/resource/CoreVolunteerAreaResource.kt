package com.pawcial.resource

import com.pawcial.dto.CreateVolunteerAreaRequest
import com.pawcial.service.CoreVolunteerAreaService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/api/core-volunteer-areas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class CoreVolunteerAreaResource {

    @Inject
    lateinit var coreVolunteerAreaService: CoreVolunteerAreaService

    @GET
    fun getAllVolunteerAreas(
        @QueryParam("volunteerId") volunteerId: UUID?,
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = coreVolunteerAreaService.findAll(volunteerId, all)

    @GET
    @Path("/{volunteerId}/{areaCode}")
    fun getVolunteerAreaById(
        @PathParam("volunteerId") volunteerId: UUID,
        @PathParam("areaCode") areaCode: String
    ) = coreVolunteerAreaService.findById(volunteerId, areaCode)

    @POST
    fun createVolunteerArea(request: CreateVolunteerAreaRequest): Response {
        val created = coreVolunteerAreaService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{volunteerId}/{areaCode}")
    fun deleteVolunteerArea(
        @PathParam("volunteerId") volunteerId: UUID,
        @PathParam("areaCode") areaCode: String
    ): Response {
        coreVolunteerAreaService.delete(volunteerId, areaCode)
        return Response.noContent().build()
    }
}


