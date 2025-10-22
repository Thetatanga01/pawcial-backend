package com.pawcial.resource

import com.pawcial.dto.CreateVolunteerActivityRequest
import com.pawcial.service.VolunteerActivityService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/volunteer-activities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class VolunteerActivityResource {

    @Inject
    lateinit var volunteerActivityService: VolunteerActivityService

    @POST
    fun createActivity(request: CreateVolunteerActivityRequest): Response {
        val created = volunteerActivityService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}

