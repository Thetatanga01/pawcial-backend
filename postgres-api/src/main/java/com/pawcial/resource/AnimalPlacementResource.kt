package com.pawcial.resource

import com.pawcial.dto.CreateAnimalPlacementRequest
import com.pawcial.service.AnimalPlacementService
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

@Path("/api/animal-placements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Animal Placements", description = "Animal placement management - Foster, Adoption, Return to Owner")
class AnimalPlacementResource {

    @Inject
    lateinit var animalPlacementService: AnimalPlacementService

    @GET
    @Operation(
        summary = "Get all placements",
        description = "Retrieve a list of animal placements with optional filters"
    )
    @APIResponse(
        responseCode = "200",
        description = "Placements retrieved successfully"
    )
    fun getAllPlacements(
        @Parameter(description = "Filter by animal ID", required = false)
        @QueryParam("animalId") animalId: UUID?,
        @Parameter(description = "Filter by person ID", required = false)
        @QueryParam("personId") personId: UUID?,
        @Parameter(description = "Include inactive placements", required = false)
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = animalPlacementService.findAll(animalId, personId, all)

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Get placement by ID",
        description = "Retrieve a specific placement by its ID"
    )
    @APIResponses(
        APIResponse(responseCode = "200", description = "Placement found"),
        APIResponse(responseCode = "404", description = "Placement not found")
    )
    fun getPlacementById(
        @Parameter(description = "Placement ID", required = true)
        @PathParam("id") id: UUID
    ) = animalPlacementService.findById(id)

    @POST
    @Operation(
        summary = "Create new placement",
        description = "Create a new animal placement (foster, adoption, return to owner)"
    )
    @APIResponses(
        APIResponse(
            responseCode = "201",
            description = "Placement created successfully"
        ),
        APIResponse(responseCode = "400", description = "Invalid request data"),
        APIResponse(responseCode = "404", description = "Referenced entity not found")
    )
    fun createPlacement(
        @Parameter(description = "Placement data", required = true)
        request: CreateAnimalPlacementRequest
    ): Response {
        val created = animalPlacementService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete placement (soft delete)",
        description = "Toggle the active status of a placement"
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Placement deleted successfully"),
        APIResponse(responseCode = "404", description = "Placement not found")
    )
    fun deletePlacement(
        @Parameter(description = "Placement ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        animalPlacementService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Hard delete placement (permanent)",
        description = "Permanently delete a placement from the database. " +
                "Only allowed within the configured time window after creation."
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Placement permanently deleted"),
        APIResponse(responseCode = "400", description = "Hard delete not allowed - time window expired"),
        APIResponse(responseCode = "404", description = "Placement not found")
    )
    fun hardDeletePlacement(
        @Parameter(description = "Placement ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        animalPlacementService.hardDelete(id)
        return Response.noContent().build()
    }
}

