package com.pawcial.resource

import com.pawcial.dto.FacilityTypeDto
import com.pawcial.dto.CreateFacilityTypeRequest
import com.pawcial.service.FacilityTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/facility-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FacilityTypeResource {

    @Inject
    lateinit var facilityTypeService: FacilityTypeService

    @GET
    fun getAllFacilityTypes(): List<FacilityTypeDto> {
        return facilityTypeService.findAll()
    }

    @POST
    fun createFacilityType(request: CreateFacilityTypeRequest): Response {
        val created = facilityTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleFacilityTypeActive(@PathParam("code") code: String): Response {
        val toggled = facilityTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

