package com.pawcial.resource

import com.pawcial.dto.CreateAssetServiceRequest
import com.pawcial.service.AssetServiceService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/api/asset-services")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AssetServiceResource {

    @Inject
    lateinit var assetServiceService: AssetServiceService

    @GET
    fun getAllServices(
        @QueryParam("assetId") assetId: UUID?,
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ) = assetServiceService.findAll(assetId, all)

    @GET
    @Path("/{id}")
    fun getServiceById(@PathParam("id") id: UUID) = assetServiceService.findById(id)

    @POST
    fun createService(request: CreateAssetServiceRequest): Response {
        val created = assetServiceService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @DELETE
    @Path("/{id}")
    fun deleteService(@PathParam("id") id: UUID): Response {
        assetServiceService.delete(id)
        return Response.noContent().build()
    }
}
