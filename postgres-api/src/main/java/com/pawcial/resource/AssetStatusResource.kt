package com.pawcial.resource

import com.pawcial.dto.AssetStatusDto
import com.pawcial.dto.CreateAssetStatusRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.AssetStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/asset-statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Asset Statuses", description = "Varlık Durumu yönetimi")
class AssetStatusResource {

    @Inject
    lateinit var assetStatusService: AssetStatusService

    @GET
    @Operation(summary = "Tüm varlık durumlarını listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllAssetStatuses(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<AssetStatusDto> {
        return assetStatusService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni varlık durumu ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createAssetStatus(request: CreateAssetStatusRequest): Response {
        val created = assetStatusService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Varlık durumu etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateAssetStatusLabel(
        @Parameter(description = "Varlık durumu kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): AssetStatusDto {
        return assetStatusService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleAssetStatusActive(
        @Parameter(description = "Varlık durumu kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = assetStatusService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Varlık durumunu kalıcı olarak sil (hard delete)",
        description = "Varlık durumunu veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Varlık durumu bulunamadı")
    fun hardDeleteAssetStatus(
        @Parameter(description = "Varlık durumu kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = assetStatusService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}