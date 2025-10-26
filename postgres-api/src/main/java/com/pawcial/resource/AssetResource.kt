package com.pawcial.resource

import com.pawcial.dto.AssetDto
import com.pawcial.dto.CreateAssetRequest
import com.pawcial.dto.UpdateAssetRequest
import com.pawcial.service.AssetService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/assets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Assets", description = "Varlık (Asset) yönetimi")
class AssetResource {

    @Inject
    lateinit var assetService: AssetService

    @GET
    @Operation(summary = "Tüm varlıkları listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllAssets(): List<AssetDto> {
        return assetService.findAll()
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "ID'ye göre varlık getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Varlık bulunamadı")
    fun getAssetById(
        @Parameter(description = "Varlık ID", required = true)
        @PathParam("id") id: UUID
    ): AssetDto {
        return assetService.findById(id)
    }

    @POST
    @Operation(summary = "Yeni varlık ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createAsset(request: CreateAssetRequest): Response {
        val created = assetService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Varlığı güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Varlık bulunamadı")
    fun updateAsset(
        @Parameter(description = "Varlık ID", required = true)
        @PathParam("id") id: UUID,
        request: UpdateAssetRequest
    ): AssetDto {
        return assetService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Varlığı sil")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Varlık bulunamadı")
    fun deleteAsset(
        @Parameter(description = "Varlık ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        assetService.delete(id)
        return Response.noContent().build()
    }
}

