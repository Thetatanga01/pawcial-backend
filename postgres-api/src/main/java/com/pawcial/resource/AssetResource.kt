package com.pawcial.resource

import com.pawcial.dto.AssetDto
import com.pawcial.service.AssetService
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/api/assets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AssetResource {

    @Inject
    lateinit var assetService: AssetService

    @GET
    fun getAllAssets(): List<AssetDto> {
        return assetService.findAll()
    }
}

