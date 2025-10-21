package com.pawcial.resource

import com.pawcial.dto.SourceTypeDto
import com.pawcial.dto.CreateSourceTypeRequest
import com.pawcial.service.SourceTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/source-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SourceTypeResource {

    @Inject
    lateinit var sourceTypeService: SourceTypeService

    @GET
    fun getAllSourceTypes(@QueryParam("all") @DefaultValue("false") all: Boolean): List<SourceTypeDto> {
        return sourceTypeService.findAll(all)
    }

    @POST
    fun createSourceType(request: CreateSourceTypeRequest): Response {
        val created = sourceTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleSourceTypeActive(@PathParam("code") code: String): Response {
        val toggled = sourceTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

