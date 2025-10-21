package com.pawcial.resource

import com.pawcial.dto.SizeDto
import com.pawcial.dto.CreateSizeRequest
import com.pawcial.service.SizeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/sizes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SizeResource {

    @Inject
    lateinit var sizeService: SizeService

    @GET
    fun getAllSizes(): List<SizeDto> {
        return sizeService.findAll()
    }

    @POST
    fun createSize(request: CreateSizeRequest): Response {
        val created = sizeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleSizeActive(@PathParam("code") code: String): Response {
        val toggled = sizeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

