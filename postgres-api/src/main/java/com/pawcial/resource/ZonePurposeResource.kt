package com.pawcial.resource

import com.pawcial.dto.ZonePurposeDto
import com.pawcial.dto.CreateZonePurposeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.ZonePurposeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/zone-purposes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Zone Purposes", description = "Bölge Amacı yönetimi")
class ZonePurposeResource {

    @Inject
    lateinit var zonePurposeService: ZonePurposeService

    @GET
    @Operation(summary = "Tüm bölge amaçlarını listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllZonePurposes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<ZonePurposeDto> {
        return zonePurposeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni bölge amacı ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createZonePurpose(request: CreateZonePurposeRequest): Response {
        val created = zonePurposeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Bölge amacı etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateZonePurposeLabel(
        @Parameter(description = "Bölge amacı kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): ZonePurposeDto {
        return zonePurposeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleZonePurposeActive(
        @Parameter(description = "Bölge amacı kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = zonePurposeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Bölge amacını kalıcı olarak sil (hard delete)",
        description = "Bölge amacını veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Bölge amacı bulunamadı")
    fun hardDeleteZonePurpose(
        @Parameter(description = "Bölge amacı kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = zonePurposeService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
