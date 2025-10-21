package com.pawcial.resource

import com.pawcial.dto.FacilityTypeDto
import com.pawcial.service.FacilityTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

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
}

