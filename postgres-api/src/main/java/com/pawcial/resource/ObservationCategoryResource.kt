package com.pawcial.resource

import com.pawcial.dto.ObservationCategoryDto
import com.pawcial.dto.CreateObservationCategoryRequest
import com.pawcial.service.ObservationCategoryService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/observation-categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ObservationCategoryResource {

    @Inject
    lateinit var observationCategoryService: ObservationCategoryService

    @GET
    fun getAllObservationCategories(@QueryParam("all") @DefaultValue("false") all: Boolean): List<ObservationCategoryDto> {
        return observationCategoryService.findAll(all)
    }

    @POST
    fun createObservationCategory(request: CreateObservationCategoryRequest): Response {
        val created = observationCategoryService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleObservationCategoryActive(@PathParam("code") code: String): Response {
        val toggled = observationCategoryService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

