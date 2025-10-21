package com.pawcial.resource

import com.pawcial.dto.UnitTypeDto
import com.pawcial.service.UnitTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/unit-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UnitTypeResource {

    @Inject
    lateinit var unitTypeService: UnitTypeService

    @GET
    fun getAllUnitTypes(): List<UnitTypeDto> {
        return unitTypeService.findAll()
    }
}

