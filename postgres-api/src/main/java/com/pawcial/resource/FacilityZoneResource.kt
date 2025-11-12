package com.pawcial.resource

import com.pawcial.dto.CreateFacilityZoneRequest
import com.pawcial.dto.FacilityZoneDto
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.UpdateFacilityZoneRequest
import com.pawcial.service.FacilityZoneService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/facility-zones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Facility Zones", description = "Tesis Bölgesi (Facility Zone) yönetimi")
class FacilityZoneResource {

    @Inject
    lateinit var facilityZoneService: FacilityZoneService

    @GET
    @Operation(summary = "Tüm tesis bölgelerini listele", description = "Aktif veya tüm tesis bölgelerini getirir (sayfalama ile)")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllFacilityZones(
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<FacilityZoneDto> {
        return facilityZoneService.findAll(all, page, size)
    }

    @GET
    @Path("/search")
    @Operation(summary = "Tesis bölgelerinde ara", description = "İsim, açıklama veya tesis adına göre arama")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun searchFacilityZones(
        @Parameter(description = "Bölge ismi ile arama")
        @QueryParam("name") name: String?,
        @Parameter(description = "Açıklama ile arama")
        @QueryParam("description") description: String?,
        @Parameter(description = "Tesis ID ile filtreleme")
        @QueryParam("facilityId") facilityId: UUID?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<FacilityZoneDto> {
        return facilityZoneService.search(name, description, facilityId, all, page, size)
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "ID'ye göre tesis bölgesi getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Tesis bölgesi bulunamadı")
    fun getFacilityZoneById(
        @Parameter(description = "Tesis bölgesi ID", required = true)
        @PathParam("id") id: UUID
    ): FacilityZoneDto {
        return facilityZoneService.findById(id)
    }

    @POST
    @Operation(summary = "Yeni tesis bölgesi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createFacilityZone(request: CreateFacilityZoneRequest): Response {
        val created = facilityZoneService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Tesis bölgesini güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Tesis bölgesi bulunamadı")
    fun updateFacilityZone(
        @Parameter(description = "Tesis bölgesi ID", required = true)
        @PathParam("id") id: UUID,
        request: UpdateFacilityZoneRequest
    ): FacilityZoneDto {
        return facilityZoneService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Tesis bölgesini sil (soft delete)")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Tesis bölgesi bulunamadı")
    fun deleteFacilityZone(
        @Parameter(description = "Tesis bölgesi ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        facilityZoneService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Tesis bölgesini kalıcı olarak sil (hard delete)",
        description = "Tesis bölgesini veritabanından kalıcı olarak siler. Sadece oluşturulduktan sonraki belirli süre içinde izin verilir."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "400", description = "Hard delete izni yok - zaman aşımı")
    @APIResponse(responseCode = "404", description = "Tesis bölgesi bulunamadı")
    fun hardDeleteFacilityZone(
        @Parameter(description = "Tesis bölgesi ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        facilityZoneService.hardDelete(id)
        return Response.noContent().build()
    }
}
