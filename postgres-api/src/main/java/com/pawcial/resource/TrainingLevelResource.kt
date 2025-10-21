package com.pawcial.resource

import com.pawcial.dto.TrainingLevelDto
import com.pawcial.service.TrainingLevelService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/training-levels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TrainingLevelResource {

    @Inject
    lateinit var trainingLevelService: TrainingLevelService

    @GET
    fun getAllTrainingLevels(): List<TrainingLevelDto> {
        return trainingLevelService.findAll()
    }
}

