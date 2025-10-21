package com.pawcial.resource

import com.pawcial.dto.HealthFlagDto
import com.pawcial.dto.CreateHealthFlagRequest
import com.pawcial.service.HealthFlagService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/health-flags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class HealthFlagResource {

    @Inject
    lateinit var healthFlagService: HealthFlagService

    @GET
    fun getAllHealthFlags(@QueryParam("all") @DefaultValue("false") all: Boolean): List<HealthFlagDto> {
        return healthFlagService.findAll(all)
    }

    @POST
    fun createHealthFlag(request: CreateHealthFlagRequest): Response {
        val created = healthFlagService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleHealthFlagActive(@PathParam("code") code: String): Response {
        val toggled = healthFlagService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

