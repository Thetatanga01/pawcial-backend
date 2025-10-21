package com.pawcial.resource

import com.pawcial.dto.ColorDto
import com.pawcial.dto.CreateColorRequest
import com.pawcial.service.ColorService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/colors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ColorResource {

    @Inject
    lateinit var colorService: ColorService

    @GET
    fun getAllColors(@QueryParam("all") @DefaultValue("false") all: Boolean): List<ColorDto> {
        return colorService.findAll(all)
    }

    @POST
    fun createColor(request: CreateColorRequest): Response {
        val created = colorService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleColorActive(@PathParam("code") code: String): Response {
        val toggled = colorService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

