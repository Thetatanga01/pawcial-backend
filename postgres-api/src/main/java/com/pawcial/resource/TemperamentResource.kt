package com.pawcial.resource

import com.pawcial.dto.TemperamentDto
import com.pawcial.dto.CreateTemperamentRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.TemperamentService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/temperaments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Temperaments", description = "Mizaç yönetimi")
class TemperamentResource {

    @Inject
    lateinit var temperamentService: TemperamentService

    @GET
    @Operation(summary = "Tüm mizaçları listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllTemperaments(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<TemperamentDto> {
        return temperamentService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni mizaç ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createTemperament(request: CreateTemperamentRequest): Response {
        val created = temperamentService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Mizaç etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateTemperamentLabel(
        @Parameter(description = "Mizaç kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): TemperamentDto {
        return temperamentService.updateLabel(code, request)
    }


    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleTemperamentActive(
        @Parameter(description = "Mizaç kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = temperamentService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
