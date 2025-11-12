package com.pawcial.resource

import com.pawcial.dto.CreateSystemParameterRequest
import com.pawcial.dto.SystemParameterDto
import com.pawcial.dto.UpdateSystemParameterRequest
import com.pawcial.service.SystemParameterService
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

@Path("/api/system-parameters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "System Parameters", description = "System parameter management - Configuration values for the application")
class SystemParameterResource {

    @Inject
    lateinit var systemParameterService: SystemParameterService

    @GET
    @Operation(
        summary = "Get all system parameters",
        description = "Retrieve all system parameters sorted by label. Parameters control system behavior like hard delete time windows."
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "System parameters retrieved successfully",
            content = [Content(schema = Schema(implementation = SystemParameterDto::class))]
        )
    )
    fun getAllParameters(
        @Parameter(description = "Include inactive parameters", required = false)
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<SystemParameterDto> {
        return systemParameterService.findAll(all)
    }

    @GET
    @Path("/{code}")
    @Operation(
        summary = "Get parameter by code",
        description = "Retrieve a specific system parameter by its code"
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "Parameter found",
            content = [Content(schema = Schema(implementation = SystemParameterDto::class))]
        ),
        APIResponse(responseCode = "404", description = "Parameter not found")
    )
    fun getParameterByCode(
        @Parameter(description = "Parameter code", required = true)
        @PathParam("code") code: String
    ): SystemParameterDto {
        return systemParameterService.findByCode(code)
    }

    @POST
    @Operation(
        summary = "Create new parameter",
        description = "Create a new system parameter. Code must contain only English letters, numbers, and underscores."
    )
    @APIResponses(
        APIResponse(
            responseCode = "201",
            description = "Parameter created successfully",
            content = [Content(schema = Schema(implementation = SystemParameterDto::class))]
        ),
        APIResponse(responseCode = "400", description = "Invalid parameter data or code already exists")
    )
    fun createParameter(
        @Parameter(description = "Parameter data", required = true)
        request: CreateSystemParameterRequest
    ): Response {
        val created = systemParameterService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(
        summary = "Update parameter",
        description = "Update an existing system parameter's value, label, or description"
    )
    @APIResponses(
        APIResponse(
            responseCode = "200",
            description = "Parameter updated successfully",
            content = [Content(schema = Schema(implementation = SystemParameterDto::class))]
        ),
        APIResponse(responseCode = "404", description = "Parameter not found")
    )
    fun updateParameter(
        @Parameter(description = "Parameter code", required = true)
        @PathParam("code") code: String,
        @Parameter(description = "Updated parameter data", required = true)
        request: UpdateSystemParameterRequest
    ): SystemParameterDto {
        return systemParameterService.update(code, request)
    }

    @DELETE
    @Path("/{code}")
    @Operation(
        summary = "Toggle parameter active status",
        description = "Toggle the active status of a system parameter (soft delete)"
    )
    @APIResponses(
        APIResponse(responseCode = "204", description = "Parameter status toggled successfully"),
        APIResponse(responseCode = "404", description = "Parameter not found")
    )
    fun toggleParameterActive(
        @Parameter(description = "Parameter code", required = true)
        @PathParam("code") code: String
    ): Response {
        systemParameterService.toggleActive(code)
        return Response.noContent().build()
    }
}

