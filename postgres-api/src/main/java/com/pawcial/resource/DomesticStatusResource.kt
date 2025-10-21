package com.pawcial.resource

import com.pawcial.dto.DomesticStatusDto
import com.pawcial.dto.CreateDomesticStatusRequest
import com.pawcial.service.DomesticStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/domestic-statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class DomesticStatusResource {

    @Inject
    lateinit var domesticStatusService: DomesticStatusService

    @GET
    fun getAllDomesticStatuses(): List<DomesticStatusDto> {
        return domesticStatusService.findAll()
    }

    @POST
    fun createDomesticStatus(request: CreateDomesticStatusRequest): Response {
        val created = domesticStatusService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleDomesticStatusActive(@PathParam("code") code: String): Response {
        val toggled = domesticStatusService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

