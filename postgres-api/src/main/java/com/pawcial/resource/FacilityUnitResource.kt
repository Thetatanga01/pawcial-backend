package com.pawcial.resource

import com.pawcial.dto.CreateFacilityUnitRequest
import com.pawcial.dto.FacilityUnitDto
import com.pawcial.dto.PagedResponse
import com.pawcial.service.FacilityUnitService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/facility-units")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Facility Units", description = "Tesis Birimi/Bölgesi (Zone) yönetimi")
class FacilityUnitResource {

    @Inject
    lateinit var facilityUnitService: FacilityUnitService

    @GET
    @Operation(summary = "Tüm birimleri listele", description = "Aktif veya tüm birimleri getirir (sayfalama ile)")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllFacilityUnits(
        @Parameter(description = "Tesise göre filtrele")
        @QueryParam("facility") facilityId: UUID?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<FacilityUnitDto> {
        return facilityUnitService.findAll(facilityId, all, page, size)
    }

    @GET
    @Path("/search")
    @Operation(summary = "Birimlerde ara", description = "Kod, tip veya tesis ismi ile arama")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun searchFacilityUnits(
        @Parameter(description = "Birim kodu ile arama")
        @QueryParam("code") code: String?,
        @Parameter(description = "Birim tipi ile arama")
        @QueryParam("type") type: String?,
        @Parameter(description = "Tesis ismi ile arama")
        @QueryParam("facilityName") facilityName: String?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<FacilityUnitDto> {
        return facilityUnitService.search(code, type, facilityName, all, page, size)
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "ID'ye göre birim getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Birim bulunamadı")
    fun getFacilityUnitById(
        @Parameter(description = "Birim ID", required = true)
        @PathParam("id") id: UUID
    ): FacilityUnitDto {
        return facilityUnitService.findById(id)
    }

    @POST
    @Operation(summary = "Yeni birim ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createUnit(request: CreateFacilityUnitRequest): Response {
        val created = facilityUnitService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}

