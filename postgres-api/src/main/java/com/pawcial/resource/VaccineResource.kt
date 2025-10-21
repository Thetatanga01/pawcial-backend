package com.pawcial.resource

import com.pawcial.dto.VaccineDto
import com.pawcial.dto.CreateVaccineRequest
import com.pawcial.service.VaccineService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/vaccines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class VaccineResource {

    @Inject
    lateinit var vaccineService: VaccineService

    @GET
    fun getAllVaccines(): List<VaccineDto> {
        return vaccineService.findAll()
    }

    @POST
    fun createVaccine(request: CreateVaccineRequest): Response {
        val created = vaccineService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleVaccineActive(@PathParam("code") code: String): Response {
        val toggled = vaccineService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

