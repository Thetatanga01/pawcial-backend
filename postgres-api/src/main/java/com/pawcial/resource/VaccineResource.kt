package com.pawcial.resource

import com.pawcial.dto.VaccineDto
import com.pawcial.dto.CreateVaccineRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.VaccineService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/vaccines")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Vaccines", description = "Aşı yönetimi")
class VaccineResource {

    @Inject
    lateinit var vaccineService: VaccineService

    @GET
    @Operation(summary = "Tüm aşıları listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllVaccines(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<VaccineDto> {
        return vaccineService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni aşı ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createVaccine(request: CreateVaccineRequest): Response {
        val created = vaccineService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Aşı etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateVaccineLabel(
        @Parameter(description = "Aşı kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): VaccineDto {
        return vaccineService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleVaccineActive(
        @Parameter(description = "Aşı kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = vaccineService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Aşıyı kalıcı olarak sil (hard delete)",
        description = "Aşıyı veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Aşı bulunamadı")
    fun hardDeleteVaccine(
        @Parameter(description = "Aşı kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = vaccineService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
