package com.pawcial.resource

import com.pawcial.dto.MedEventTypeDto
import com.pawcial.service.MedEventTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

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
}

