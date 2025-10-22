package com.pawcial.resource

import com.pawcial.dto.CreateFacilityRequest
import com.pawcial.dto.FacilityDto
import com.pawcial.service.FacilityService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/facilities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FacilityResource {

    @Inject
    lateinit var facilityService: FacilityService

    @GET
    fun getAllFacilities(): List<FacilityDto> {
        return facilityService.findAll()
    }

    @POST
    fun createFacility(request: CreateFacilityRequest): Response {
        val created = facilityService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}

