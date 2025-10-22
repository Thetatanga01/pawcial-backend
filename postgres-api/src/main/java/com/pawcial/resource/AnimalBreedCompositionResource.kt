package com.pawcial.resource

import com.pawcial.dto.CreateAnimalBreedCompositionRequest
import com.pawcial.service.AnimalBreedCompositionService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/animal-breed-compositions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnimalBreedCompositionResource {

    @Inject
    lateinit var animalBreedCompositionService: AnimalBreedCompositionService

    @POST
    fun createComposition(request: CreateAnimalBreedCompositionRequest): Response {
        val created = animalBreedCompositionService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}

