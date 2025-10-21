package com.pawcial.resource

import com.pawcial.dto.DomesticStatusDto
import com.pawcial.service.DomesticStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

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
}

