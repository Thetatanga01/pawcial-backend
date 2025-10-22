package com.pawcial.resource

import com.pawcial.dto.BreedDto
import com.pawcial.dto.CreateBreedRequest
import com.pawcial.service.BreedService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/breeds")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class BreedResource {

    @Inject
    lateinit var breedService: BreedService

    @GET
    fun getAllBreeds(): List<BreedDto> {
        return breedService.findAll()
    }

    @POST
    fun createBreed(request: CreateBreedRequest): Response {
        val created = breedService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}

