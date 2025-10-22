package com.pawcial.resource

import com.pawcial.dto.AssetDto
import com.pawcial.service.AssetService
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
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

    @POST
    fun createAsset(request: com.pawcial.dto.CreateAssetRequest): jakarta.ws.rs.core.Response {
        val created = assetService.create(request)
        return jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.CREATED).entity(created).build()
    }
}

