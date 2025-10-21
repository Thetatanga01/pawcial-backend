package com.pawcial.resource

import com.pawcial.dto.EventTypeDto
import com.pawcial.dto.CreateEventTypeRequest
import com.pawcial.service.EventTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/event-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class EventTypeResource {

    @Inject
    lateinit var eventTypeService: EventTypeService

    @GET
    fun getAllEventTypes(): List<EventTypeDto> {
        return eventTypeService.findAll()
    }

    @POST
    fun createEventType(request: CreateEventTypeRequest): Response {
        val created = eventTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleEventTypeActive(@PathParam("code") code: String): Response {
        val toggled = eventTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

