package com.pawcial.resource

import com.pawcial.dto.CreateVolunteerActivityRequest
import com.pawcial.service.VolunteerActivityService
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

@Path("/api/volunteer-activities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Volunteer Activities", description = "Volunteer activity tracking and management")
class VolunteerActivityResource {

    @Inject
    lateinit var volunteerActivityService: VolunteerActivityService

    @GET
    @Operation(
        summary = "Get all volunteer activities",
        description = "Retrieve volunteer activities, optionally filtered by volunteer ID"
    )
    @APIResponse(responseCode = "200", description = "Activities retrieved successfully")
    fun getAllActivities(
        @Parameter(description = "Filter by volunteer ID", required = false)
        @QueryParam("volunteerId") volunteerId: UUID?,
        @Parameter(description = "Include inactive activities", required = false)
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = volunteerActivityService.findAll(volunteerId, all)

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Get activity by ID",
        description = "Retrieve a specific volunteer activity by its ID"
    )
    @APIResponses(
        APIResponse(responseCode = "200", description = "Activity found"),
        APIResponse(responseCode = "404", description = "Activity not found")
    )
    fun getActivityById(
        @Parameter(description = "Activity ID", required = true)
        @PathParam("id") id: UUID
    ) = volunteerActivityService.findById(id)

    @POST
    @Operation(
        summary = "Create new activity",
        description = "Record a new volunteer activity"
    )
    @APIResponses(
        APIResponse(responseCode = "201", description = "Activity created successfully"),
        APIResponse(responseCode = "400", description = "Invalid request data"),
        APIResponse(responseCode = "404", description = "Referenced entity not found")
    )
    fun createActivity(
        @Parameter(description = "Activity data", required = true)
        request: CreateVolunteerActivityRequest
    ): Response {
        val created = volunteerActivityService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{id}")
    @Operation(
        summary = "Delete activity (soft delete)",
        description = "Toggle the active status of a volunteer activity"
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Activity deleted successfully"),
        APIResponse(responseCode = "404", description = "Activity not found")
    )
    fun deleteActivity(
        @Parameter(description = "Activity ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        volunteerActivityService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Hard delete activity (permanent)",
        description = "Permanently delete a volunteer activity from the database. " +
                "Only allowed within the configured time window after creation."
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Activity permanently deleted"),
        APIResponse(responseCode = "400", description = "Hard delete not allowed - time window expired"),
        APIResponse(responseCode = "404", description = "Activity not found")
    )
    fun hardDeleteActivity(
        @Parameter(description = "Activity ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        volunteerActivityService.hardDelete(id)
        return Response.noContent().build()
    }
}

