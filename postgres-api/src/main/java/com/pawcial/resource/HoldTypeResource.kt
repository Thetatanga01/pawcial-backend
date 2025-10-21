package com.pawcial.resource

import com.pawcial.dto.HoldTypeDto
import com.pawcial.service.HoldTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/hold-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class HoldTypeResource {

    @Inject
    lateinit var holdTypeService: HoldTypeService

    @GET
    fun getAllHoldTypes(): List<HoldTypeDto> {
        return holdTypeService.findAll()
    }
}

