package com.pawcial.resource

import com.pawcial.dto.ServiceTypeDto
import com.pawcial.dto.CreateServiceTypeRequest
import com.pawcial.service.ServiceTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/service-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ServiceTypeResource {

    @Inject
    lateinit var serviceTypeService: ServiceTypeService

    @GET
    fun getAllServiceTypes(@QueryParam("all") @DefaultValue("false") all: Boolean): List<ServiceTypeDto> {
        return serviceTypeService.findAll(all)
    }

    @POST
    fun createServiceType(request: CreateServiceTypeRequest): Response {
        val created = serviceTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleServiceTypeActive(@PathParam("code") code: String): Response {
        val toggled = serviceTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

