package com.pawcial.resource

import com.pawcial.dto.EventTypeDto
import com.pawcial.service.EventTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

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
}

