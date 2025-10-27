package com.pawcial.resource

import com.pawcial.dto.AssetDto
import com.pawcial.dto.CreateAssetRequest
import com.pawcial.dto.PagedResponse
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
    @Operation(summary = "Tüm varlıkları listele", description = "Aktif veya tüm varlıkları getirir (sayfalama ile)")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllAssets(
        @Parameter(description = "Tesise göre filtrele")
        @QueryParam("facility") facilityId: UUID?,
        @Parameter(description = "Duruma göre filtrele")
        @QueryParam("status") status: String?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<AssetDto> {
        return assetService.findAll(facilityId, status, all, page, size)
    }

    @GET
    @Path("/search")
    @Operation(summary = "Varlıklarda ara", description = "İsim, kod veya tipe göre arama")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun searchAssets(
        @Parameter(description = "Varlık ismi ile arama")
        @QueryParam("name") name: String?,
        @Parameter(description = "Varlık kodu ile arama")
        @QueryParam("code") code: String?,
        @Parameter(description = "Varlık tipi ile arama")
        @QueryParam("type") type: String?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<AssetDto> {
        return assetService.search(name, code, type, all, page, size)
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

