package com.pawcial.resource

import com.pawcial.dto.HealthFlagDto
import com.pawcial.service.HealthFlagService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/health-flags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class HealthFlagResource {

    @Inject
    lateinit var healthFlagService: HealthFlagService

    @GET
    fun getAllHealthFlags(): List<HealthFlagDto> {
        return healthFlagService.findAll()
    }
}

