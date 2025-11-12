package com.pawcial.resource

import com.pawcial.dto.CreateAnimalObservationRequest
import com.pawcial.service.AnimalObservationService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/animal-observations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Animal Observations", description = "Animal observation and notes management")
class AnimalObservationResource {

    @Inject
    lateinit var animalObservationService: AnimalObservationService

    @GET
    @Operation(
        summary = "Get all observations",
        description = "Retrieve a list of animal observations with optional filters"
    )
    @APIResponse(
        responseCode = "200",
        description = "Observations retrieved successfully"
    )
    fun getAllObservations(
        @Parameter(description = "Filter by animal ID", required = false)
        @QueryParam("animalId") animalId: UUID?,
        @Parameter(description = "Include inactive observations", required = false)
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = animalObservationService.findAll(animalId, all)

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Get observation by ID",
        description = "Retrieve a specific observation by its ID"
    )
    @APIResponses(
        APIResponse(responseCode = "200", description = "Observation found"),
        APIResponse(responseCode = "404", description = "Observation not found")
    )
    fun getObservationById(
        @Parameter(description = "Observation ID", required = true)
        @PathParam("id") id: UUID
    ) = animalObservationService.findById(id)

    @POST
    @Operation(
        summary = "Create new observation",
        description = "Create a new animal observation or note"
    )
    @APIResponses(
        APIResponse(
            responseCode = "201",
            description = "Observation created successfully"
        ),
        APIResponse(responseCode = "400", description = "Invalid request data"),
        APIResponse(responseCode = "404", description = "Referenced entity not found")
    )
    fun createObservation(
        @Parameter(description = "Observation data", required = true)
        request: CreateAnimalObservationRequest
    ): Response {
        val created = animalObservationService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete observation (soft delete)",
        description = "Toggle the active status of an observation"
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Observation deleted successfully"),
        APIResponse(responseCode = "404", description = "Observation not found")
    )
    fun deleteObservation(
        @Parameter(description = "Observation ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        animalObservationService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Hard delete observation (permanent)",
        description = "Permanently delete an observation from the database. " +
                "Only allowed within the configured time window after creation."
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Observation permanently deleted"),
        APIResponse(responseCode = "400", description = "Hard delete not allowed - time window expired"),
        APIResponse(responseCode = "404", description = "Observation not found")
    )
    fun hardDeleteObservation(
        @Parameter(description = "Observation ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        animalObservationService.hardDelete(id)
        return Response.noContent().build()
    }
}

