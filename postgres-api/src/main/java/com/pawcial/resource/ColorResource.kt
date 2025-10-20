package com.pawcial.resource

import com.pawcial.dto.ColorDto
import com.pawcial.service.ColorService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/colors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ColorResource {

    @Inject
    lateinit var colorService: ColorService

    @GET
    fun getAllColors(): List<ColorDto> {
        return colorService.findAll()
    }
}

