package com.pawcial.resource

import com.pawcial.dto.UnitTypeDto
import com.pawcial.dto.CreateUnitTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.UnitTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/unit-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Unit Types", description = "Birim Tipi yönetimi")
class UnitTypeResource {

    @Inject
    lateinit var unitTypeService: UnitTypeService

    @GET
    @Operation(summary = "Tüm birim tiplerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllUnitTypes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<UnitTypeDto> {
        return unitTypeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni birim tipi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createUnitType(request: CreateUnitTypeRequest): Response {
        val created = unitTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Birim tipi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateUnitTypeLabel(
        @Parameter(description = "Birim tipi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): UnitTypeDto {
        return unitTypeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleUnitTypeActive(
        @Parameter(description = "Birim tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = unitTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Birim tipini kalıcı olarak sil (hard delete)",
        description = "Birim tipini veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Birim tipi bulunamadı")
    fun hardDeleteUnitType(
        @Parameter(description = "Birim tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = unitTypeService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
