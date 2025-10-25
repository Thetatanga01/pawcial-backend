package com.pawcial.resource

import com.pawcial.dto.AssetTypeDto
import com.pawcial.dto.CreateAssetTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.AssetTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/asset-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Asset Types", description = "Varlık Tipi yönetimi")
class AssetTypeResource {

    @Inject
    lateinit var assetTypeService: AssetTypeService

    @GET
    @Operation(summary = "Tüm varlık tiplerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllAssetTypes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<AssetTypeDto> {
        return assetTypeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni varlık tipi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createAssetType(request: CreateAssetTypeRequest): Response {
        val created = assetTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Varlık tipi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateAssetTypeLabel(
        @Parameter(description = "Varlık tipi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): AssetTypeDto {
        return assetTypeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleAssetTypeActive(
        @Parameter(description = "Varlık tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = assetTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
