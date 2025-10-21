package com.pawcial.resource

import com.pawcial.dto.SourceTypeDto
import com.pawcial.service.SourceTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/source-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SourceTypeResource {

    @Inject
    lateinit var sourceTypeService: SourceTypeService

    @GET
    fun getAllSourceTypes(): List<SourceTypeDto> {
        return sourceTypeService.findAll()
    }
}

