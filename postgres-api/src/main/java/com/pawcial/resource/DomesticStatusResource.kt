package com.pawcial.resource

import com.pawcial.dto.DomesticStatusDto
import com.pawcial.dto.CreateDomesticStatusRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.DomesticStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/domestic-statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Domestic Statuses", description = "Evcilleştirme Durumu yönetimi")
class DomesticStatusResource {

    @Inject
    lateinit var domesticStatusService: DomesticStatusService

    @GET
    @Operation(summary = "Tüm evcilleştirme durumlarını listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllDomesticStatuses(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<DomesticStatusDto> {
        return domesticStatusService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni evcilleştirme durumu ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createDomesticStatus(request: CreateDomesticStatusRequest): Response {
        val created = domesticStatusService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Etiket güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateDomesticStatusLabel(
        @Parameter(description = "Kod", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): DomesticStatusDto {
        return domesticStatusService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleDomesticStatusActive(
        @Parameter(description = "Kod", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = domesticStatusService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
