package com.pawcial.resource

import com.pawcial.dto.ZonePurposeDto
import com.pawcial.service.ZonePurposeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/zone-purposes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ZonePurposeResource {

    @Inject
    lateinit var zonePurposeService: ZonePurposeService

    @GET
    fun getAllZonePurposes(): List<ZonePurposeDto> {
        return zonePurposeService.findAll()
    }
}


