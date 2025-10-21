package com.pawcial.resource

import com.pawcial.dto.PlacementStatusDto
import com.pawcial.dto.CreatePlacementStatusRequest
import com.pawcial.service.PlacementStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/placement-statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PlacementStatusResource {

    @Inject
    lateinit var placementStatusService: PlacementStatusService

    @GET
    fun getAllPlacementStatuses(@QueryParam("all") @DefaultValue("false") all: Boolean): List<PlacementStatusDto> {
        return placementStatusService.findAll(all)
    }

    @POST
    fun createPlacementStatus(request: CreatePlacementStatusRequest): Response {
        val created = placementStatusService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun togglePlacementStatusActive(@PathParam("code") code: String): Response {
        val toggled = placementStatusService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

