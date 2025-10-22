package com.pawcial.resource

import com.pawcial.dto.FacilityUnitDto
import com.pawcial.service.FacilityUnitService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/facility-units")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FacilityUnitResource {

    @Inject
    lateinit var facilityUnitService: FacilityUnitService

    @GET
    fun getAllFacilityUnits(): List<FacilityUnitDto> {
        return facilityUnitService.findAll()
    }

    @POST
    fun createUnit(request: com.pawcial.dto.CreateFacilityUnitRequest): jakarta.ws.rs.core.Response {
        val created = facilityUnitService.create(request)
        return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.CREATED).entity(created).build()
    }
}

