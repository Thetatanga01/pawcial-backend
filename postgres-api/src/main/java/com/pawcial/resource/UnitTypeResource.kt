package com.pawcial.resource

import com.pawcial.dto.UnitTypeDto
import com.pawcial.dto.CreateUnitTypeRequest
import com.pawcial.service.UnitTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

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

    @POST
    fun createUnitType(request: CreateUnitTypeRequest): Response {
        val created = unitTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleUnitTypeActive(@PathParam("code") code: String): Response {
        val toggled = unitTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

