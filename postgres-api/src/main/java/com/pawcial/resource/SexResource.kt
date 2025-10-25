package com.pawcial.resource

import com.pawcial.dto.SexDto
import com.pawcial.dto.CreateSexRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.SexService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/sexes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Sexes", description = "Cinsiyet yönetimi")
class SexResource {

    @Inject
    lateinit var sexService: SexService

    @GET
    @Operation(summary = "Tüm cinsiyetleri listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllSexes(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<SexDto> {
        return sexService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni cinsiyet ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createSex(request: CreateSexRequest): Response {
        val created = sexService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Cinsiyet etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateSexLabel(
        @Parameter(description = "Cinsiyet kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): SexDto {
        return sexService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleSexActive(
        @Parameter(description = "Cinsiyet kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = sexService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
