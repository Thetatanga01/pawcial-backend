package com.pawcial.resource

import com.pawcial.dto.AssetStatusDto
import com.pawcial.service.AssetStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/asset-statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AssetStatusResource {

    @Inject
    lateinit var assetStatusService: AssetStatusService

    @GET
    fun getAllAssetStatuses(): List<AssetStatusDto> {
        return assetStatusService.findAll()
    }
}