package com.pawcial.resource

import com.pawcial.dto.CreateAnimalPlacementRequest
import com.pawcial.service.AnimalPlacementService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/api/animal-placements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnimalPlacementResource {

    @Inject
    lateinit var animalPlacementService: AnimalPlacementService

    @GET
    fun getAllPlacements(
        @QueryParam("animalId") animalId: UUID?,
        @QueryParam("personId") personId: UUID?,
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = animalPlacementService.findAll(animalId, personId, all)

    @GET
    @Path("/{id}")
    fun getPlacementById(@PathParam("id") id: UUID) = animalPlacementService.findById(id)

    @POST
    fun createPlacement(request: CreateAnimalPlacementRequest): Response {
        val created = animalPlacementService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{id}")
    fun deletePlacement(@PathParam("id") id: UUID): Response {
        animalPlacementService.delete(id)
        return Response.noContent().build()
    }
}

