package com.pawcial.resource

import com.pawcial.dto.MedEventTypeDto
import com.pawcial.dto.CreateMedEventTypeRequest
import com.pawcial.service.MedEventTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/med-event-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class MedEventTypeResource {

    @Inject
    lateinit var medEventTypeService: MedEventTypeService

    @GET
    fun getAllMedEventTypes(): List<MedEventTypeDto> {
        return medEventTypeService.findAll()
    }

    @POST
    fun createMedEventType(request: CreateMedEventTypeRequest): Response {
        val created = medEventTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleMedEventTypeActive(@PathParam("code") code: String): Response {
        val toggled = medEventTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

