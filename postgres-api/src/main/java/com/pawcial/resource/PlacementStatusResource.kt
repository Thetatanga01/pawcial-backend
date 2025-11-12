package com.pawcial.resource

import com.pawcial.dto.PlacementStatusDto
import com.pawcial.dto.CreatePlacementStatusRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.PlacementStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/placement-statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Placement Statuses", description = "Yerleştirme Durumu yönetimi")
class PlacementStatusResource {

    @Inject
    lateinit var placementStatusService: PlacementStatusService

    @GET
    @Operation(summary = "Tüm yerleştirme durumlarını listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllPlacementStatuses(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<PlacementStatusDto> {
        return placementStatusService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni yerleştirme durumu ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createPlacementStatus(request: CreatePlacementStatusRequest): Response {
        val created = placementStatusService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Yerleştirme durumu etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updatePlacementStatusLabel(
        @Parameter(description = "Yerleştirme durumu kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): PlacementStatusDto {
        return placementStatusService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun togglePlacementStatusActive(
        @Parameter(description = "Yerleştirme durumu kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = placementStatusService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
