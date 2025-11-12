package com.pawcial.resource

import com.pawcial.dto.CreateOrganizationRequest
import com.pawcial.dto.OrganizationDto
import com.pawcial.dto.UpdateOrganizationRequest
import com.pawcial.service.OrganizationService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Organizations", description = "Organizasyon (Kurum) sözlüğü yönetimi")
class OrganizationResource {

    @Inject
    lateinit var organizationService: OrganizationService

    @GET
    @Operation(summary = "Tüm organizasyonları listele", description = "Aktif veya tüm organizasyonları getirir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllOrganizations(
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<OrganizationDto> {
        return organizationService.findAll(all)
    }

    @GET
    @Path("/{code}")
    @Operation(summary = "Koda göre organizasyon getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Organizasyon bulunamadı")
    fun getOrganizationByCode(
        @Parameter(description = "Organizasyon kodu", required = true)
        @PathParam("code") code: String
    ): OrganizationDto {
        return organizationService.findById(code)
    }

    @POST
    @Operation(summary = "Yeni organizasyon ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createOrganization(request: CreateOrganizationRequest): Response {
        val created = organizationService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Organizasyonu güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Organizasyon bulunamadı")
    fun updateOrganization(
        @Parameter(description = "Organizasyon kodu", required = true)
        @PathParam("code") code: String,
        request: UpdateOrganizationRequest
    ): OrganizationDto {
        return organizationService.update(code, request)
    }

    @DELETE
    @Path("/{code}")
    @Operation(summary = "Organizasyonu sil (soft delete)")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Organizasyon bulunamadı")
    fun deleteOrganization(
        @Parameter(description = "Organizasyon kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        organizationService.delete(code)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Organizasyonu kalıcı olarak sil (hard delete)",
        description = "Organizasyonu veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Organizasyon bulunamadı")
    fun hardDeleteOrganization(
        @Parameter(description = "Organizasyon kodu", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = organizationService.hardDelete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

