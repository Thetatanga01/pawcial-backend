package com.pawcial.resource

import com.pawcial.dto.DoseRouteDto
import com.pawcial.service.DoseRouteService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/dose-routes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class DoseRouteResource {

    @Inject
    lateinit var doseRouteService: DoseRouteService

    @GET
    fun getAllDoseRoutes(): List<DoseRouteDto> {
        return doseRouteService.findAll()
    }
}

