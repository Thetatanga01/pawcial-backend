package com.pawcial.resource

import com.pawcial.dto.LeashBehaviorDto
import com.pawcial.dto.CreateLeashBehaviorRequest
import com.pawcial.dto.UpdateLabelRequest
import com.pawcial.service.LeashBehaviorService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/leash-behaviors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Leash Behaviors", description = "Tasma ile Uyum / Leash Behavior etiketleri yönetimi")
class LeashBehaviorResource {

    @Inject
    lateinit var leashBehaviorService: LeashBehaviorService

    @GET
    @Operation(summary = "Tüm tasma davranışlarını listele", description = "Aktif veya tüm tasma davranışlarını getirir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllLeashBehaviors(
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<LeashBehaviorDto> {
        return leashBehaviorService.findAll(all)
    }

    @POST
    @Operation(summary = "Yeni tasma davranışı ekle", description = "Sisteme yeni bir tasma davranışı ekler")
    @APIResponse(responseCode = "201", description = "Tasma davranışı başarıyla oluşturuldu")
    @APIResponse(responseCode = "409", description = "Bu kod zaten mevcut")
    fun createLeashBehavior(request: CreateLeashBehaviorRequest): Response {
        val created = leashBehaviorService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Tasma davranışı etiketini güncelle", description = "Mevcut bir tasma davranışının etiketini günceller (kod değiştirilemez)")
    @APIResponse(responseCode = "200", description = "Etiket başarıyla güncellendi")
    @APIResponse(responseCode = "409", description = "Tasma davranışı bulunamadı")
    fun updateLeashBehaviorLabel(
        @Parameter(description = "Tasma davranışı kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateLabelRequest
    ): LeashBehaviorDto {
        return leashBehaviorService.updateLabel(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Tasma davranışı aktiflik durumunu değiştir", description = "Tasma davranışının aktif/pasif durumunu değiştirir")
    @APIResponse(responseCode = "200", description = "Durum başarıyla değiştirildi")
    @APIResponse(responseCode = "404", description = "Tasma davranışı bulunamadı")
    fun toggleLeashBehaviorActive(
        @Parameter(description = "Tasma davranışı kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val toggled = leashBehaviorService.toggleActive(code)
        return if (toggled) {
            Response.ok().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Tasma davranışını kalıcı olarak sil (hard delete)",
        description = "Tasma davranışını veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Tasma davranışı bulunamadı")
    fun hardDeleteLeashBehavior(
        @Parameter(description = "Tasma davranışı kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = leashBehaviorService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

