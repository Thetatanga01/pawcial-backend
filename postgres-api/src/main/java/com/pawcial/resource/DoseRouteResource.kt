package com.pawcial.resource

import com.pawcial.dto.DoseRouteDto
import com.pawcial.dto.CreateDoseRouteRequest
import com.pawcial.service.DoseRouteService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

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

    @POST
    fun createDoseRoute(request: CreateDoseRouteRequest): Response {
        val created = doseRouteService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleDoseRouteActive(@PathParam("code") code: String): Response {
        val toggled = doseRouteService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

