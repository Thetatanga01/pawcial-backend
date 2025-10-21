package com.pawcial.resource

import com.pawcial.dto.TemperamentDto
import com.pawcial.service.TemperamentService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/temperaments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TemperamentResource {

    @Inject
    lateinit var temperamentService: TemperamentService

    @GET
    fun getAllTemperaments(): List<TemperamentDto> {
        return temperamentService.findAll()
    }
}

