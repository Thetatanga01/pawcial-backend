package com.pawcial.resource

import com.pawcial.dto.CreateAnimalObservationRequest
import com.pawcial.service.AnimalObservationService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/api/animal-observations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AnimalObservationResource {

    @Inject
    lateinit var animalObservationService: AnimalObservationService

    @GET
    fun getAllObservations(
        @QueryParam("animalId") animalId: UUID?,
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = animalObservationService.findAll(animalId, all)

    @GET
    @Path("/{id}")
    fun getObservationById(@PathParam("id") id: UUID) = animalObservationService.findById(id)

    @POST
    fun createObservation(request: CreateAnimalObservationRequest): Response {
        val created = animalObservationService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{id}")
    fun deleteObservation(@PathParam("id") id: UUID): Response {
        animalObservationService.delete(id)
        return Response.noContent().build()
    }
}

