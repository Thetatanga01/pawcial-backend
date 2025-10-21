package com.pawcial.resource

import com.pawcial.dto.OutcomeTypeDto
import com.pawcial.service.OutcomeTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/outcome-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class OutcomeTypeResource {

    @Inject
    lateinit var outcomeTypeService: OutcomeTypeService

    @GET
    fun getAllOutcomeTypes(): List<OutcomeTypeDto> {
        return outcomeTypeService.findAll()
    }
}

