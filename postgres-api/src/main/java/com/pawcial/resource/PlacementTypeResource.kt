package com.pawcial.resource

import com.pawcial.dto.PlacementTypeDto
import com.pawcial.dto.CreatePlacementTypeRequest
import com.pawcial.service.PlacementTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

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

    @POST
    fun createPlacementType(request: CreatePlacementTypeRequest): Response {
        val created = placementTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun togglePlacementTypeActive(@PathParam("code") code: String): Response {
        val toggled = placementTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

