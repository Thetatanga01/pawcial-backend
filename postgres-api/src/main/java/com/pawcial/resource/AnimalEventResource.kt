package com.pawcial.resource

import com.pawcial.dto.CreateAnimalEventRequest
import com.pawcial.service.AnimalEventService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/api/animal-events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnimalEventResource {

    @Inject
    lateinit var animalEventService: AnimalEventService

    @GET
    fun getAllEvents(
        @QueryParam("animalId") animalId: UUID?,
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = animalEventService.findAll(animalId, all)

    @GET
    @Path("/{id}")
    fun getEventById(@PathParam("id") id: UUID) = animalEventService.findById(id)

    @POST
    fun createEvent(request: CreateAnimalEventRequest): Response {
        val created = animalEventService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{id}")
    fun deleteEvent(@PathParam("id") id: UUID): Response {
        animalEventService.delete(id)
        return Response.noContent().build()
    }
}

