package com.pawcial.resource

import com.pawcial.dto.MedEventTypeDto
import com.pawcial.dto.CreateMedEventTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.MedEventTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/med-event-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Med Event Types", description = "Tıbbi Olay Tipi yönetimi")
class MedEventTypeResource {

    @Inject
    lateinit var medEventTypeService: MedEventTypeService

    @GET
    @Operation(summary = "Tüm tıbbi olay tiplerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllMedEventTypes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<MedEventTypeDto> {
        return medEventTypeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni tıbbi olay tipi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createMedEventType(request: CreateMedEventTypeRequest): Response {
        val created = medEventTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Tıbbi olay tipi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateMedEventTypeLabel(
        @Parameter(description = "Tıbbi olay tipi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): MedEventTypeDto {
        return medEventTypeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleMedEventTypeActive(
        @Parameter(description = "Tıbbi olay tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = medEventTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
