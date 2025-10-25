package com.pawcial.resource

import com.pawcial.dto.EventTypeDto
import com.pawcial.dto.CreateEventTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.EventTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/event-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Event Types", description = "Olay Tipi yönetimi")
class EventTypeResource {

    @Inject
    lateinit var eventTypeService: EventTypeService

    @GET
    @Operation(summary = "Tüm olay tiplerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllEventTypes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<EventTypeDto> {
        return eventTypeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni olay tipi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createEventType(request: CreateEventTypeRequest): Response {
        val created = eventTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Olay tipi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateEventTypeLabel(
        @Parameter(description = "Olay tipi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): EventTypeDto {
        return eventTypeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleEventTypeActive(
        @Parameter(description = "Olay tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = eventTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
