package com.pawcial.resource

import com.pawcial.dto.SizeDto
import com.pawcial.service.SizeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/sizes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SizeResource {

    @Inject
    lateinit var sizeService: SizeService

    @GET
    fun getAllSizes(): List<SizeDto> {
        return sizeService.findAll()
    }
}

