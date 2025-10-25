package com.pawcial.resource

import com.pawcial.dto.ServiceTypeDto
import com.pawcial.dto.CreateServiceTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.ServiceTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/service-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Service Types", description = "Hizmet Tipi yönetimi")
class ServiceTypeResource {

    @Inject
    lateinit var serviceTypeService: ServiceTypeService

    @GET
    @Operation(summary = "Tüm hizmet tiplerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllServiceTypes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<ServiceTypeDto> {
        return serviceTypeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni hizmet tipi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createServiceType(request: CreateServiceTypeRequest): Response {
        val created = serviceTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Hizmet tipi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateServiceTypeLabel(
        @Parameter(description = "Hizmet tipi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): ServiceTypeDto {
        return serviceTypeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleServiceTypeActive(
        @Parameter(description = "Hizmet tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = serviceTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
