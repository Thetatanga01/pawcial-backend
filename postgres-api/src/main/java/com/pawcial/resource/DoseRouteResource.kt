package com.pawcial.resource

import com.pawcial.dto.DoseRouteDto
import com.pawcial.dto.CreateDoseRouteRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.DoseRouteService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/dose-routes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Dose Routes", description = "Doz Uygulama Yolu yönetimi")
class DoseRouteResource {

    @Inject
    lateinit var doseRouteService: DoseRouteService

    @GET
    @Operation(summary = "Tüm doz uygulama yollarını listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllDoseRoutes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<DoseRouteDto> {
        return doseRouteService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni doz uygulama yolu ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createDoseRoute(request: CreateDoseRouteRequest): Response {
        val created = doseRouteService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Doz uygulama yolu etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateDoseRouteLabel(
        @Parameter(description = "Doz uygulama yolu kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): DoseRouteDto {
        return doseRouteService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleDoseRouteActive(
        @Parameter(description = "Doz uygulama yolu kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = doseRouteService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Doz uygulama yolunu kalıcı olarak sil (hard delete)",
        description = "Doz uygulama yolunu veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Doz uygulama yolu bulunamadı")
    fun hardDeleteDoseRoute(
        @Parameter(description = "Doz uygulama yolu kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = doseRouteService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
