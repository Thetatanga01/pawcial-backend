package com.pawcial.resource

import com.pawcial.dto.HoldTypeDto
import com.pawcial.dto.CreateHoldTypeRequest
import com.pawcial.service.HoldTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/hold-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class HoldTypeResource {

    @Inject
    lateinit var holdTypeService: HoldTypeService

    @GET
    fun getAllHoldTypes(@QueryParam("all") @DefaultValue("false") all: Boolean): List<HoldTypeDto> {
        return holdTypeService.findAll(all)
    }

    @POST
    fun createHoldType(request: CreateHoldTypeRequest): Response {
        val created = holdTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleHoldTypeActive(@PathParam("code") code: String): Response {
        val toggled = holdTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

