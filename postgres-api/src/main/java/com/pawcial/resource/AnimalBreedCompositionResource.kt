package com.pawcial.resource

import com.pawcial.dto.CreateAnimalBreedCompositionRequest
import com.pawcial.service.AnimalBreedCompositionService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/api/animal-breed-compositions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnimalBreedCompositionResource {

    @Inject
    lateinit var animalBreedCompositionService: AnimalBreedCompositionService

    @GET
    fun getAllCompositions(
        @QueryParam("animalId") animalId: UUID?,
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = animalBreedCompositionService.findAll(animalId, all)

    @GET
    @Path("/{animalId}/{breedId}")
    fun getCompositionById(
        @PathParam("animalId") animalId: UUID,
        @PathParam("breedId") breedId: UUID
    ) = animalBreedCompositionService.findById(animalId, breedId)

    @POST
    fun createComposition(request: CreateAnimalBreedCompositionRequest): Response {
        val created = animalBreedCompositionService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{animalId}/{breedId}")
    fun deleteComposition(
        @PathParam("animalId") animalId: UUID,
        @PathParam("breedId") breedId: UUID
    ): Response {
        animalBreedCompositionService.delete(animalId, breedId)
        return Response.noContent().build()
    }
}

