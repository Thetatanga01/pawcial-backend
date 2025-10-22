package com.pawcial.resource

import com.pawcial.dto.CreateVolunteerActivityRequest
import com.pawcial.service.VolunteerActivityService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/api/volunteer-activities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class VolunteerActivityResource {

    @Inject
    lateinit var volunteerActivityService: VolunteerActivityService

    @GET
    fun getAllActivities(@QueryParam("volunteerId") volunteerId: UUID?) = volunteerActivityService.findAll(volunteerId)

    @GET
    @Path("/{id}")
    fun getActivityById(@PathParam("id") id: UUID) = volunteerActivityService.findById(id)

    @POST
    fun createActivity(request: CreateVolunteerActivityRequest): Response {
        val created = volunteerActivityService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{id}")
    fun deleteActivity(@PathParam("id") id: UUID): Response {
        volunteerActivityService.delete(id)
        return Response.noContent().build()
    }
}

