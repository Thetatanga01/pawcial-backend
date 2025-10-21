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
}

