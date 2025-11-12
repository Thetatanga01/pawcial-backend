package com.pawcial.resource

import com.pawcial.dto.SizeDto
import com.pawcial.dto.CreateSizeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.SizeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/sizes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Sizes", description = "Boyut yönetimi")
class SizeResource {

    @Inject
    lateinit var sizeService: SizeService

    @GET
    @Operation(summary = "Tüm boyutları listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllSizes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<SizeDto> {
        return sizeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni boyut ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createSize(request: CreateSizeRequest): Response {
        val created = sizeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
    @PUT
    @Path("/{code}")
    @Operation(summary = "Boyut etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateSizeLabel(
        @Parameter(description = "Boyut kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): SizeDto {
        return sizeService.updateLabel(code, request)
    }


    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleSizeActive(
        @Parameter(description = "Boyut kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = sizeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Boyutu kalıcı olarak sil (hard delete)",
        description = "Boyutu veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Boyut bulunamadı")
    fun hardDeleteSize(
        @Parameter(description = "Boyut kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = sizeService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
