package com.pawcial.resource

import com.pawcial.dto.PlacementStatusDto
import com.pawcial.service.PlacementStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/placement-statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PlacementStatusResource {

    @Inject
    lateinit var placementStatusService: PlacementStatusService

    @GET
    fun getAllPlacementStatuses(): List<PlacementStatusDto> {
        return placementStatusService.findAll()
    }
}

