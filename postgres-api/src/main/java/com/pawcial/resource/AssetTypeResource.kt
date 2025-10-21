package com.pawcial.resource

import com.pawcial.dto.AssetTypeDto
import com.pawcial.dto.CreateAssetTypeRequest
import com.pawcial.service.AssetTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/asset-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AssetTypeResource {

    @Inject
    lateinit var assetTypeService: AssetTypeService

    @GET
    fun getAllAssetTypes(@QueryParam("all") @DefaultValue("false") all: Boolean): List<AssetTypeDto> {
        return assetTypeService.findAll(all)
    }

    @POST
    fun createAssetType(request: CreateAssetTypeRequest): Response {
        val created = assetTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleAssetTypeActive(@PathParam("code") code: String): Response {
        val toggled = assetTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

