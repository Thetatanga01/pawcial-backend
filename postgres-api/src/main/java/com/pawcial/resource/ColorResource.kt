package com.pawcial.resource

import com.pawcial.dto.ColorDto
import com.pawcial.dto.CreateColorRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.ColorService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/colors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Colors", description = "Renk (Color) yönetimi için endpoint'ler")
class ColorResource {

    @Inject
    lateinit var colorService: ColorService

    @GET
    @Operation(summary = "Tüm renkleri listele", description = "Aktif veya tüm renkleri getirir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllColors(
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<ColorDto> {
        return colorService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni renk ekle", description = "Sisteme yeni bir renk ekler")
    @APIResponse(responseCode = "201", description = "Renk başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Bu kod zaten mevcut")
    fun createColor(request: CreateColorRequest): Response {
        val created = colorService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
    @PUT
    @Path("/{code}")
    @Operation(summary = "Renk etiketini güncelle", description = "Mevcut bir rengin etiketini günceller (kod değiştirilemez)")
    @APIResponse(responseCode = "200", description = "Etiket başarıyla güncellendi")
    @APIResponse(responseCode = "409", description = "Renk bulunamadı")
    fun updateColorLabel(
        @Parameter(description = "Renk kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): ColorDto {
        return colorService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Renk aktiflik durumunu değiştir", description = "Rengin aktif/pasif durumunu değiştirir")
    @APIResponse(responseCode = "200", description = "Durum başarıyla değiştirildi")
    @APIResponse(responseCode = "404", description = "Renk bulunamadı")
    fun toggleColorActive(
        @Parameter(description = "Renk kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = colorService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Rengi kalıcı olarak sil (hard delete)",
        description = "Rengi veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Renk bulunamadı")
    fun hardDeleteColor(
        @Parameter(description = "Renk kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = colorService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}


