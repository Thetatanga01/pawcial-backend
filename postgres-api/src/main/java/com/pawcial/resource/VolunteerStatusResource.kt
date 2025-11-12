package com.pawcial.resource

import com.pawcial.dto.VolunteerStatusDto
import com.pawcial.dto.CreateVolunteerStatusRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.VolunteerStatusService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/volunteer-statuses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Volunteer Statuses", description = "Gönüllü Durumu yönetimi")
class VolunteerStatusResource {

    @Inject
    lateinit var volunteerStatusService: VolunteerStatusService

    @GET
    @Operation(summary = "Tüm gönüllü durumlarını listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllVolunteerStatuses(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<VolunteerStatusDto> {
        return volunteerStatusService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni gönüllü durumu ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createVolunteerStatus(request: CreateVolunteerStatusRequest): Response {
        val created = volunteerStatusService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Gönüllü durumu etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateVolunteerStatusLabel(
        @Parameter(description = "Gönüllü durumu kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): VolunteerStatusDto {
        return volunteerStatusService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleVolunteerStatusActive(
        @Parameter(description = "Gönüllü durumu kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = volunteerStatusService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Gönüllü durumunu kalıcı olarak sil (hard delete)",
        description = "Gönüllü durumunu veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Gönüllü durumu bulunamadı")
    fun hardDeleteVolunteerStatus(
        @Parameter(description = "Gönüllü durumu kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = volunteerStatusService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
