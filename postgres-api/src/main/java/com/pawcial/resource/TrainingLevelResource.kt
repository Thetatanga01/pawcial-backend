package com.pawcial.resource

import com.pawcial.dto.TrainingLevelDto
import com.pawcial.dto.CreateTrainingLevelRequest
import com.pawcial.service.TrainingLevelService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/training-levels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TrainingLevelResource {

    @Inject
    lateinit var trainingLevelService: TrainingLevelService

    @GET
    fun getAllTrainingLevels(@QueryParam("all") @DefaultValue("false") all: Boolean): List<TrainingLevelDto> {
        return trainingLevelService.findAll(all)
    }

    @POST
    fun createTrainingLevel(request: CreateTrainingLevelRequest): Response {
        val created = trainingLevelService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleTrainingLevelActive(@PathParam("code") code: String): Response {
        val toggled = trainingLevelService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

