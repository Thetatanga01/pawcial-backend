package com.pawcial.resource

import com.pawcial.dto.SourceTypeDto
import com.pawcial.dto.CreateSourceTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.SourceTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/source-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Source Types", description = "Kaynak Tipi yönetimi")
class SourceTypeResource {

    @Inject
    lateinit var sourceTypeService: SourceTypeService

    @GET
    @Operation(summary = "Tüm kaynak tiplerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllSourceTypes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<SourceTypeDto> {
        return sourceTypeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni kaynak tipi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createSourceType(request: CreateSourceTypeRequest): Response {
        val created = sourceTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Kaynak tipi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateSourceTypeLabel(
        @Parameter(description = "Kaynak tipi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): SourceTypeDto {
        return sourceTypeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleSourceTypeActive(
        @Parameter(description = "Kaynak tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = sourceTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Kaynak tipini kalıcı olarak sil (hard delete)",
        description = "Kaynak tipini veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Kaynak tipi bulunamadı")
    fun hardDeleteSourceType(
        @Parameter(description = "Kaynak tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = sourceTypeService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
