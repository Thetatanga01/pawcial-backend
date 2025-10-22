package com.pawcial.resource

import com.pawcial.dto.FacilityZoneDto
import com.pawcial.service.FacilityZoneService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/facility-zones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FacilityZoneResource {

    @Inject
    lateinit var facilityZoneService: FacilityZoneService

    @GET
    fun getAllFacilityZones(): List<FacilityZoneDto> {
        return facilityZoneService.findAll()
    }

    @POST
    fun createZone(request: com.pawcial.dto.CreateFacilityZoneRequest): jakarta.ws.rs.core.Response {
        val created = facilityZoneService.create(request)
        return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.CREATED).entity(created).build()
    }
}

