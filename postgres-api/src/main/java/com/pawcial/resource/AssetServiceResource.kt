package com.pawcial.resource

import com.pawcial.dto.CreateAssetServiceRequest
import com.pawcial.service.AssetServiceService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/api/asset-services")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AssetServiceResource {

    @Inject
    lateinit var assetServiceService: AssetServiceService

    @POST
    fun createService(request: CreateAssetServiceRequest): Response {
        val created = assetServiceService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}
