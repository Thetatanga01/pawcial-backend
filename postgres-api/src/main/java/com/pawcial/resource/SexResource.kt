package com.pawcial.resource

import com.pawcial.dto.SexDto
import com.pawcial.dto.CreateSexRequest
import com.pawcial.service.SexService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/sexes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SexResource {

    @Inject
    lateinit var sexService: SexService

    @GET
    fun getAllSexes(@QueryParam("all") @DefaultValue("false") all: Boolean): List<SexDto> {
        return sexService.findAll(all)
    }

    @POST
    fun createSex(request: CreateSexRequest): Response {
        val created = sexService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleSexActive(@PathParam("code") code: String): Response {
        val toggled = sexService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

