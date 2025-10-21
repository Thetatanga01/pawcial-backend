package com.pawcial.resource

import com.pawcial.dto.VaccineDto
import com.pawcial.service.VaccineService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/vaccines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class VaccineResource {

    @Inject
    lateinit var vaccineService: VaccineService

    @GET
    fun getAllVaccines(): List<VaccineDto> {
        return vaccineService.findAll()
    }
}

