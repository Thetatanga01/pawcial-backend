package com.pawcial.resource

import com.pawcial.dto.CreateAnimalBreedCompositionRequest
import com.pawcial.service.AnimalBreedCompositionService
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

@Path("/api/animal-breed-compositions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Animal Breed Compositions", description = "Animal breed composition management for mixed breed animals")
class AnimalBreedCompositionResource {

    @Inject
    lateinit var animalBreedCompositionService: AnimalBreedCompositionService

    @GET
    @Operation(
        summary = "Get all breed compositions",
        description = "Retrieve breed compositions, optionally filtered by animal ID"
    )
    @APIResponse(responseCode = "200", description = "Breed compositions retrieved successfully")
    fun getAllCompositions(
        @Parameter(description = "Filter by animal ID", required = false)
        @QueryParam("animalId") animalId: UUID?,
        @Parameter(description = "Include inactive compositions", required = false)
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = animalBreedCompositionService.findAll(animalId, all)

    @GET
    @Path("/{animalId}/{breedId}")
    @Operation(
        summary = "Get breed composition by IDs",
        description = "Retrieve a specific breed composition by animal ID and breed ID"
    )
    @APIResponses(
        APIResponse(responseCode = "200", description = "Breed composition found"),
        APIResponse(responseCode = "404", description = "Breed composition not found")
    )
    fun getCompositionById(
        @Parameter(description = "Animal ID", required = true)
        @PathParam("animalId") animalId: UUID,
        @Parameter(description = "Breed ID", required = true)
        @PathParam("breedId") breedId: UUID
    ) = animalBreedCompositionService.findById(animalId, breedId)

    @POST
    @Operation(
        summary = "Create new breed composition",
        description = "Add a breed to an animal's composition (for mixed breeds)"
    )
    @APIResponses(
        APIResponse(responseCode = "201", description = "Breed composition created successfully"),
        APIResponse(responseCode = "400", description = "Invalid request data"),
        APIResponse(responseCode = "404", description = "Referenced entity not found")
    )
    fun createComposition(
        @Parameter(description = "Breed composition data", required = true)
        request: CreateAnimalBreedCompositionRequest
    ): Response {
        val created = animalBreedCompositionService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{animalId}/{breedId}")
    @Operation(
        summary = "Delete breed composition (soft delete)",
        description = "Toggle the active status of a breed composition"
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Breed composition deleted successfully"),
        APIResponse(responseCode = "404", description = "Breed composition not found")
    )
    fun deleteComposition(
        @Parameter(description = "Animal ID", required = true)
        @PathParam("animalId") animalId: UUID,
        @Parameter(description = "Breed ID", required = true)
        @PathParam("breedId") breedId: UUID
    ): Response {
        animalBreedCompositionService.delete(animalId, breedId)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{animalId}/{breedId}/hard-delete")
    @Operation(
        summary = "Hard delete breed composition (permanent)",
        description = "Permanently delete a breed composition from the database. " +
                "Only allowed within the configured time window after creation."
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Breed composition permanently deleted"),
        APIResponse(responseCode = "400", description = "Hard delete not allowed - time window expired"),
        APIResponse(responseCode = "404", description = "Breed composition not found")
    )
    fun hardDeleteComposition(
        @Parameter(description = "Animal ID", required = true)
        @PathParam("animalId") animalId: UUID,
        @Parameter(description = "Breed ID", required = true)
        @PathParam("breedId") breedId: UUID
    ): Response {
        animalBreedCompositionService.hardDelete(animalId, breedId)
        return Response.noContent().build()
    }
}

