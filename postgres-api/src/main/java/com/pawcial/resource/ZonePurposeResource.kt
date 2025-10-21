package com.pawcial.resource

import com.pawcial.dto.ZonePurposeDto
import com.pawcial.dto.CreateZonePurposeRequest
import com.pawcial.service.ZonePurposeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/zone-purposes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ZonePurposeResource {

    @Inject
    lateinit var zonePurposeService: ZonePurposeService

    @GET
    fun getAllZonePurposes(@QueryParam("all") @DefaultValue("false") all: Boolean): List<ZonePurposeDto> {
        return zonePurposeService.findAll(all)
    }

    @POST
    fun createZonePurpose(request: CreateZonePurposeRequest): Response {
        val created = zonePurposeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleZonePurposeActive(@PathParam("code") code: String): Response {
        val toggled = zonePurposeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}


