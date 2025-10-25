package com.pawcial.resource

import com.pawcial.dto.ObservationCategoryDto
import com.pawcial.dto.CreateObservationCategoryRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.ObservationCategoryService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/observation-categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Observation Categories", description = "Gözlem Kategorisi yönetimi")
class ObservationCategoryResource {

    @Inject
    lateinit var observationCategoryService: ObservationCategoryService

    @GET
    @Operation(summary = "Tüm gözlem kategorilerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllObservationCategories(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<ObservationCategoryDto> {
        return observationCategoryService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni gözlem kategorisi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createObservationCategory(request: CreateObservationCategoryRequest): Response {
        val created = observationCategoryService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Gözlem kategorisi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateObservationCategoryLabel(
        @Parameter(description = "Gözlem kategorisi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): ObservationCategoryDto {
        return observationCategoryService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleObservationCategoryActive(
        @Parameter(description = "Gözlem kategorisi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = observationCategoryService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
