package com.pawcial.resource

import com.pawcial.dto.AssetStatusDto
import com.pawcial.dto.CreateAssetStatusRequest
import com.pawcial.service.AssetStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

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

    @POST
    fun createAssetStatus(request: CreateAssetStatusRequest): Response {
        val created = assetStatusService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PATCH
    @Path("/{code}/toggle")
    fun toggleAssetStatusActive(@PathParam("code") code: String): Response {
        val toggled = assetStatusService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}