package com.pawcial.resource

import com.pawcial.dto.ServiceTypeDto
import com.pawcial.service.ServiceTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/service-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ServiceTypeResource {

    @Inject
    lateinit var serviceTypeService: ServiceTypeService

    @GET
    fun getAllServiceTypes(): List<ServiceTypeDto> {
        return serviceTypeService.findAll()
    }
}

