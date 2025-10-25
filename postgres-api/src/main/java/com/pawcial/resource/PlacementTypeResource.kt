package com.pawcial.resource

import com.pawcial.dto.PlacementTypeDto
import com.pawcial.dto.CreatePlacementTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.PlacementTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/placement-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Placement Types", description = "Yerleştirme Tipi yönetimi")
class PlacementTypeResource {

    @Inject
    lateinit var placementTypeService: PlacementTypeService

    @GET
    @Operation(summary = "Tüm yerleştirme tiplerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllPlacementTypes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<PlacementTypeDto> {
        return placementTypeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni yerleştirme tipi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createPlacementType(request: CreatePlacementTypeRequest): Response {
        val created = placementTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Yerleştirme tipi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updatePlacementTypeLabel(
        @Parameter(description = "Yerleştirme tipi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): PlacementTypeDto {
        return placementTypeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun togglePlacementTypeActive(
        @Parameter(description = "Yerleştirme tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = placementTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
