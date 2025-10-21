package com.pawcial.resource

import com.pawcial.dto.SexDto
import com.pawcial.service.SexService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/sexes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SexResource {

    @Inject
    lateinit var sexService: SexService

    @GET
    fun getAllSexes(): List<SexDto> {
        return sexService.findAll()
    }
}

