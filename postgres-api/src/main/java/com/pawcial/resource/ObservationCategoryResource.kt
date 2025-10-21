package com.pawcial.resource

import com.pawcial.dto.ObservationCategoryDto
import com.pawcial.service.ObservationCategoryService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/observation-categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ObservationCategoryResource {

    @Inject
    lateinit var observationCategoryService: ObservationCategoryService

    @GET
    fun getAllObservationCategories(): List<ObservationCategoryDto> {
        return observationCategoryService.findAll()
    }
}

