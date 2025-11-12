package com.pawcial.resource

import com.pawcial.dto.AnimalEventDto
import com.pawcial.dto.CreateAnimalEventRequest
import com.pawcial.dto.UpdateAnimalEventRequest
import com.pawcial.service.AnimalEventService
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

@Path("/api/animal-events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Animal Events", description = "Animal event management endpoints - Intake, Transfer, Medical, Outcome, Hold events")
class AnimalEventResource {

    @Inject
    lateinit var animalEventService: AnimalEventService

    @GET
    @Path("/animal/{animalId}")
    @Operation(
        summary = "Get events by animal ID with pagination",
        description = "Retrieve paginated animal events for a specific animal. Events are sorted by event_at in descending order (newest first)."
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "Paginated list of animal events",
            content = [Content(schema = Schema(implementation = Object::class))]
        )
    )
    fun getEventsByAnimal(
        @Parameter(description = "Animal ID", required = true)
        @PathParam("animalId") animalId: UUID,
        @Parameter(description = "Page number (0-based)", required = false)
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Page size", required = false)
        @QueryParam("size") @DefaultValue("20") size: Int,
        @Parameter(description = "Include inactive events", required = false)
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): Response {
        val events = animalEventService.findAll(animalId, all)
        val totalElements = events.size.toLong()
        val totalPages = if (size > 0) ((totalElements + size - 1) / size).toInt() else 1

        val pagedEvents = if (size > 0) {
            events.drop(page * size).take(size)
        } else {
            events
        }

        val response = mapOf(
            "content" to pagedEvents,
            "page" to page,
            "size" to size,
            "totalElements" to totalElements,
            "totalPages" to totalPages,
            "hasNext" to (page < totalPages - 1),
            "hasPrevious" to (page > 0)
        )

        return Response.ok(response).build()
    }

    @GET
    @Operation(
        summary = "Get all animal events",
        description = "Retrieve a list of all animal events. Can be filtered by animal ID and active status. Events are sorted by event_at in descending order (newest first)."
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "List of animal events retrieved successfully",
            content = [Content(schema = Schema(implementation = AnimalEventDto::class))]
        )
    )
    fun getAllEvents(
        @Parameter(description = "Filter events by animal ID", required = false)
        @QueryParam("animalId") animalId: UUID?,
        @Parameter(description = "Include inactive events", required = false)
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<AnimalEventDto> = animalEventService.findAll(animalId, all)

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Get event by ID",
        description = "Retrieve a specific animal event by its ID"
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "Event found",
            content = [Content(schema = Schema(implementation = AnimalEventDto::class))]
        ),
        APIResponse(responseCode = "404", description = "Event not found")
    )
    fun getEventById(
        @Parameter(description = "Event ID", required = true)
        @PathParam("id") id: UUID
    ): AnimalEventDto = animalEventService.findById(id)

    @POST
    @Operation(
        summary = "Create new event",
        description = "Create a new animal event (INTAKE, TRANSFER, MEDICAL, OUTCOME, HOLD_START, HOLD_END)"
    )
    @APIResponses(
        APIResponse(
            responseCode = "201",
            description = "Event created successfully",
            content = [Content(schema = Schema(implementation = AnimalEventDto::class))]
        ),
        APIResponse(responseCode = "400", description = "Invalid request data"),
        APIResponse(responseCode = "404", description = "Referenced entity not found")
    )
    fun createEvent(
        @Parameter(description = "Event data", required = true)
        request: CreateAnimalEventRequest
    ): Response {
        val created = animalEventService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(
        summary = "Update event",
        description = "Update an existing animal event. Note: Events older than 30 minutes cannot be updated (read-only)."
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "Event updated successfully",
            content = [Content(schema = Schema(implementation = AnimalEventDto::class))]
        ),
        APIResponse(responseCode = "400", description = "Invalid request data or event is read-only (older than 30 minutes)"),
        APIResponse(responseCode = "404", description = "Event not found")
    )
    fun updateEvent(
        @Parameter(description = "Event ID", required = true)
        @PathParam("id") id: UUID,
        @Parameter(description = "Updated event data", required = true)
        request: UpdateAnimalEventRequest
    ): AnimalEventDto {
        return animalEventService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete event (soft delete)",
        description = "Toggle the active status of an animal event (soft delete)."
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Event deleted successfully"),
        APIResponse(responseCode = "404", description = "Event not found")
    )
    fun deleteEvent(
        @Parameter(description = "Event ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        animalEventService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Hard delete event (permanent)",
        description = "Permanently delete an animal event from the database. " +
                "Only allowed within the configured time window after creation. " +
                "After the time window expires, only soft delete is available."
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Event permanently deleted"),
        APIResponse(responseCode = "400", description = "Hard delete not allowed - time window expired"),
        APIResponse(responseCode = "404", description = "Event not found")
    )
    fun hardDeleteEvent(
        @Parameter(description = "Event ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        animalEventService.hardDelete(id)
        return Response.noContent().build()
    }
}

