package com.pawcial.resource

import com.pawcial.dto.TrainingLevelDto
import com.pawcial.dto.CreateTrainingLevelRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.TrainingLevelService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/training-levels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Training Levels", description = "Eğitim Seviyesi yönetimi")
class TrainingLevelResource {

    @Inject
    lateinit var trainingLevelService: TrainingLevelService

    @GET
    @Operation(summary = "Tüm eğitim seviyelerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllTrainingLevels(
        @Parameter(description = "Tüm kayıtları getir")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<TrainingLevelDto> {
        return trainingLevelService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni eğitim seviyesi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Kod zaten mevcut")
    fun createTrainingLevel(request: CreateTrainingLevelRequest): Response {
        val created = trainingLevelService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Eğitim seviyesi etiketini güncelle")
    @APIResponse(responseCode = "200", description = "Güncellendi")
    fun updateTrainingLevelLabel(
        @Parameter(description = "Eğitim seviyesi kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): TrainingLevelDto {
        return trainingLevelService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Aktiflik durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Değiştirildi")
    fun toggleTrainingLevelActive(
        @Parameter(description = "Eğitim seviyesi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = trainingLevelService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Eğitim seviyesini kalıcı olarak sil (hard delete)",
        description = "Eğitim seviyesini veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Eğitim seviyesi bulunamadı")
    fun hardDeleteTrainingLevel(
        @Parameter(description = "Eğitim seviyesi kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = trainingLevelService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}
