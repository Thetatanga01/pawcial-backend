package com.pawcial.resource

import com.pawcial.dto.FacilityTypeDto
import com.pawcial.dto.CreateFacilityTypeRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.FacilityTypeService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/facility-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Facility Types", description = "Tesis Tipi yönetimi")
class FacilityTypeResource {

    @Inject
    lateinit var facilityTypeService: FacilityTypeService

    @GET
    @Operation(summary = "Tüm tesis tiplerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllFacilityTypes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<FacilityTypeDto> {
        return facilityTypeService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni tesis tipi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createFacilityType(request: CreateFacilityTypeRequest): Response {
        val created = facilityTypeService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Tesis tipi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateFacilityTypeLabel(
        @Parameter(description = "Tesis tipi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): FacilityTypeDto {
        return facilityTypeService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleFacilityTypeActive(
        @Parameter(description = "Tesis tipi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = facilityTypeService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
