package com.pawcial.resource

import com.pawcial.dto.SpeciesDto
import com.pawcial.service.SpeciesService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/species")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SpeciesResource {

    @Inject
    lateinit var speciesService: SpeciesService

    @GET
    fun getAllSpecies(): List<SpeciesDto> {
        return speciesService.findAll()
    }
}

