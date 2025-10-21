package com.pawcial.resource

import com.pawcial.dto.PlacementTypeDto
import com.pawcial.service.PlacementTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/placement-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PlacementTypeResource {

    @Inject
    lateinit var placementTypeService: PlacementTypeService

    @GET
    fun getAllPlacementTypes(): List<PlacementTypeDto> {
        return placementTypeService.findAll()
    }
}

