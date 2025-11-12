package com.pawcial.resource

import com.pawcial.dto.HoldTypeDto
import com.pawcial.dto.CreateHoldTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.HoldTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/hold-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Hold Types", description = "Tutma Tipi yönetimi")
class HoldTypeResource {

    @Inject
    lateinit var holdTypeService: HoldTypeService

    @GET
    @Operation(summary = "Tüm tutma tiplerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllHoldTypes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<HoldTypeDto> {
        return holdTypeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni tutma tipi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createHoldType(request: CreateHoldTypeRequest): Response {
        val created = holdTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Tutma tipi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateHoldTypeLabel(
        @Parameter(description = "Tutma tipi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): HoldTypeDto {
        return holdTypeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleHoldTypeActive(
        @Parameter(description = "Tutma tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = holdTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Tutma tipini kalıcı olarak sil (hard delete)",
        description = "Tutma tipini veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Tutma tipi bulunamadı")
    fun hardDeleteHoldType(
        @Parameter(description = "Tutma tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = holdTypeService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
