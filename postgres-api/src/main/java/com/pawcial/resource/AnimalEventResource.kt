package com.pawcial.resource

import com.pawcial.dto.CreateAnimalEventRequest
import com.pawcial.service.AnimalEventService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/animal-events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnimalEventResource {

    @Inject
    lateinit var animalEventService: AnimalEventService

    @POST
    fun createEvent(request: CreateAnimalEventRequest): Response {
        val created = animalEventService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}

