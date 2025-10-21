package com.pawcial.resource

import com.pawcial.dto.TemperamentDto
import com.pawcial.dto.CreateTemperamentRequest
import com.pawcial.service.TemperamentService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/temperaments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TemperamentResource {

    @Inject
    lateinit var temperamentService: TemperamentService

    @GET
    fun getAllTemperaments(): List<TemperamentDto> {
        return temperamentService.findAll()
    }

    @POST
    fun createTemperament(request: CreateTemperamentRequest): Response {
        val created = temperamentService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleTemperamentActive(@PathParam("code") code: String): Response {
        val toggled = temperamentService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

