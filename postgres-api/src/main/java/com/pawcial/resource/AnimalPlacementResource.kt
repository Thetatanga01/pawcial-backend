package com.pawcial.resource

import com.pawcial.dto.CreateAnimalPlacementRequest
import com.pawcial.service.AnimalPlacementService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/animal-placements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnimalPlacementResource {

    @Inject
    lateinit var animalPlacementService: AnimalPlacementService

    @POST
    fun createPlacement(request: CreateAnimalPlacementRequest): Response {
        val created = animalPlacementService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}

