package com.pawcial.resource

import com.pawcial.dto.CreateAnimalObservationRequest
import com.pawcial.service.AnimalObservationService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/animal-observations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnimalObservationResource {

    @Inject
    lateinit var animalObservationService: AnimalObservationService

    @POST
    fun createObservation(request: CreateAnimalObservationRequest): Response {
        val created = animalObservationService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}

