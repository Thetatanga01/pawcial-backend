package com.pawcial.resource

import com.pawcial.dto.VolunteerAreaDto
import com.pawcial.dto.CreateVolunteerAreaDictionaryRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.VolunteerAreaService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/volunteer-areas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Volunteer Areas", description = "Gönüllü Alanı yönetimi")
class VolunteerAreaResource {

    @Inject
    lateinit var volunteerAreaService: VolunteerAreaService

    @GET
    @Operation(summary = "Tüm gönüllü alanlarını listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllVolunteerAreas(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<VolunteerAreaDto> {
        return volunteerAreaService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni gönüllü alanı ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createVolunteerArea(request: CreateVolunteerAreaDictionaryRequest): Response {
        val created = volunteerAreaService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Gönüllü alanı etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateVolunteerAreaLabel(
        @Parameter(description = "Gönüllü alanı kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): VolunteerAreaDto {
        return volunteerAreaService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleVolunteerAreaActive(
        @Parameter(description = "Gönüllü alanı kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = volunteerAreaService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
