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
}

