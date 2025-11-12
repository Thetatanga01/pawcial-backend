package com.pawcial.resource

import com.pawcial.dto.*
import com.pawcial.service.AnimalService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/animals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Animals", description = "Animal management endpoints")
class AnimalResource {

    @Inject
    lateinit var animalService: AnimalService

    @GET
    @Operation(
        summary = "Get all animals",
        description = "Retrieve a paginated list of animals with optional filters"
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "Animals retrieved successfully",
            content = [Content(schema = Schema(implementation = PagedResponse::class))]
        )
    )
    fun getAllAnimals(
        @Parameter(description = "Filter by species ID", required = false)
        @QueryParam("species") speciesId: UUID?,
        @Parameter(description = "Filter by status", required = false)
        @QueryParam("status") status: String?,
        @Parameter(description = "Include inactive animals", required = false)
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Page number (0-based)", required = false)
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Page size", required = false)
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<AnimalDto> {
        return animalService.findAll(speciesId, status, all, page, size)
    }

    @GET
    @Path("/search")
    @Operation(
        summary = "Search animals",
        description = "Search animals by name, species name, or breed name with pagination"
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "Search results retrieved successfully",
            content = [Content(schema = Schema(implementation = PagedResponse::class))]
        )
    )
    fun searchAnimals(
        @Parameter(description = "Search by animal name", required = false)
        @QueryParam("name") name: String?,
        @Parameter(description = "Search by species name", required = false)
        @QueryParam("speciesName") speciesName: String?,
        @Parameter(description = "Search by breed name", required = false)
        @QueryParam("breedName") breedName: String?,
        @Parameter(description = "Include inactive animals", required = false)
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Page number (0-based)", required = false)
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Page size", required = false)
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<AnimalDto> {
        return animalService.search(name, speciesName, breedName, all, page, size)
    }

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Get animal by ID",
        description = "Retrieve a specific animal by its ID"
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "Animal found",
            content = [Content(schema = Schema(implementation = AnimalDto::class))]
        ),
        APIResponse(responseCode = "404", description = "Animal not found")
    )
    fun getAnimalById(
        @Parameter(description = "Animal ID", required = true)
        @PathParam("id") id: UUID
    ): AnimalDto {
        return animalService.findById(id)
    }

    @POST
    @Operation(
        summary = "Create new animal",
        description = "Create a new animal record in the system"
    )
    @APIResponses(
        APIResponse(
            responseCode = "201",
            description = "Animal created successfully",
            content = [Content(schema = Schema(implementation = AnimalDto::class))]
        ),
        APIResponse(responseCode = "400", description = "Invalid request data"),
        APIResponse(responseCode = "404", description = "Referenced entity not found")
    )
    fun createAnimal(
        @Parameter(description = "Animal data", required = true)
        request: CreateAnimalRequest
    ): Response {
        val created = animalService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(
        summary = "Update animal",
        description = "Update an existing animal's information"
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "Animal updated successfully",
            content = [Content(schema = Schema(implementation = AnimalDto::class))]
        ),
        APIResponse(responseCode = "400", description = "Invalid request data"),
        APIResponse(responseCode = "404", description = "Animal not found")
    )
    fun updateAnimal(
        @Parameter(description = "Animal ID", required = true)
        @PathParam("id") id: UUID,
        @Parameter(description = "Updated animal data", required = true)
        request: UpdateAnimalRequest
    ): AnimalDto {
        return animalService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete animal (soft delete)",
        description = "Toggle the active status of an animal (soft delete). Use /hard-delete for permanent deletion."
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Animal deleted successfully"),
        APIResponse(responseCode = "404", description = "Animal not found")
    )
    fun deleteAnimal(
        @Parameter(description = "Animal ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        animalService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Hard delete animal (permanent)",
        description = "Permanently delete an animal from the database. " +
                "Only allowed within the configured time window after creation (default: 5 minutes). " +
                "After the time window expires, only soft delete is available."
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Animal permanently deleted"),
        APIResponse(responseCode = "400", description = "Hard delete not allowed - time window expired"),
        APIResponse(responseCode = "404", description = "Animal not found")
    )
    fun hardDeleteAnimal(
        @Parameter(description = "Animal ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        animalService.hardDelete(id)
        return Response.noContent().build()
    }
}