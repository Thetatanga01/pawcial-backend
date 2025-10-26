package com.pawcial.resource

import com.pawcial.dto.*
import com.pawcial.service.AnimalService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/api/animals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnimalResource {

    @Inject
    lateinit var animalService: AnimalService

    @GET
    fun getAllAnimals(
        @QueryParam("species") speciesId: UUID?,
        @QueryParam("status") status: String?,
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<AnimalDto> {
        return animalService.findAll(speciesId, status, all)
    }

    @GET
    @Path("/{id}")
    fun getAnimalById(@PathParam("id") id: UUID): AnimalDto {
        return animalService.findById(id)
    }

    @POST
    fun createAnimal(request: CreateAnimalRequest): Response {
        val created = animalService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    fun updateAnimal(
        @PathParam("id") id: UUID,
        request: UpdateAnimalRequest
    ): AnimalDto {
        return animalService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    fun deleteAnimal(@PathParam("id") id: UUID): Response {
        animalService.delete(id)
        return Response.noContent().build()
    }
}