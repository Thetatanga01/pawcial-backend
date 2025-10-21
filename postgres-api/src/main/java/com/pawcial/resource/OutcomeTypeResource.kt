package com.pawcial.resource

import com.pawcial.dto.OutcomeTypeDto
import com.pawcial.dto.CreateOutcomeTypeRequest
import com.pawcial.service.OutcomeTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/outcome-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class OutcomeTypeResource {

    @Inject
    lateinit var outcomeTypeService: OutcomeTypeService

    @GET
    fun getAllOutcomeTypes(@QueryParam("all") @DefaultValue("false") all: Boolean): List<OutcomeTypeDto> {
        return outcomeTypeService.findAll(all)
    }

    @POST
    fun createOutcomeType(request: CreateOutcomeTypeRequest): Response {
        val created = outcomeTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleOutcomeTypeActive(@PathParam("code") code: String): Response {
        val toggled = outcomeTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

