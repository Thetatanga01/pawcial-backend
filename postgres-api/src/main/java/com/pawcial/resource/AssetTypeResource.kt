package com.pawcial.resource

import com.pawcial.dto.AssetTypeDto
import com.pawcial.service.AssetTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/asset-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AssetTypeResource {

    @Inject
    lateinit var assetTypeService: AssetTypeService

    @GET
    fun getAllAssetTypes(): List<AssetTypeDto> {
        return assetTypeService.findAll()
    }
}

