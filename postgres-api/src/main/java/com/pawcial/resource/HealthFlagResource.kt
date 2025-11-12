package com.pawcial.resource

import com.pawcial.dto.HealthFlagDto
import com.pawcial.dto.CreateHealthFlagRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.HealthFlagService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/health-flags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Health Flags", description = "Sağlık İşareti yönetimi")
class HealthFlagResource {

    @Inject
    lateinit var healthFlagService: HealthFlagService

    @GET
    @Operation(summary = "Tüm sağlık işaretlerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllHealthFlags(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<HealthFlagDto> {
        return healthFlagService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni sağlık işareti ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createHealthFlag(request: CreateHealthFlagRequest): Response {
        val created = healthFlagService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Sağlık işareti etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateHealthFlagLabel(
        @Parameter(description = "Sağlık işareti kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): HealthFlagDto {
        return healthFlagService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleHealthFlagActive(
        @Parameter(description = "Sağlık işareti kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = healthFlagService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Sağlık işaretini kalıcı olarak sil (hard delete)",
        description = "Sağlık işaretini veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Sağlık işareti bulunamadı")
    fun hardDeleteHealthFlag(
        @Parameter(description = "Sağlık işareti kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = healthFlagService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
