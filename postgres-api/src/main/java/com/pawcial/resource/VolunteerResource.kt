package com.pawcial.resource

import com.pawcial.dto.CreateVolunteerRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.UpdateVolunteerRequest
import com.pawcial.dto.VolunteerDto
import com.pawcial.service.VolunteerService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/volunteers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Volunteers", description = "Gönüllü (Volunteer) yönetimi")
class VolunteerResource {

    @Inject
    lateinit var volunteerService: VolunteerService

    @GET
    @Operation(summary = "Tüm gönüllüleri listele", description = "Aktif veya tüm gönüllüleri getirir (sayfalama ile)")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllVolunteers(
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<VolunteerDto> {
        return volunteerService.findAll(all, page, size)
    }

    @GET
    @Path("/search")
    @Operation(summary = "Gönüllülerde ara", description = "İsim, durum veya gönüllü koduna göre arama")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun searchVolunteers(
        @Parameter(description = "Kişi ismi ile arama")
        @QueryParam("personName") personName: String?,
        @Parameter(description = "Durum ile arama")
        @QueryParam("status") status: String?,
        @Parameter(description = "Gönüllü kodu ile arama")
        @QueryParam("volunteerCode") volunteerCode: String?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<VolunteerDto> {
        return volunteerService.search(personName, status, volunteerCode, all, page, size)
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "ID'ye göre gönüllü getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Gönüllü bulunamadı")
    fun getVolunteerById(
        @Parameter(description = "Gönüllü ID", required = true)
        @PathParam("id") id: UUID
    ): VolunteerDto {
        return volunteerService.findById(id)
    }

    @POST
    @Operation(summary = "Yeni gönüllü ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createVolunteer(request: CreateVolunteerRequest): Response {
        val created = volunteerService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Gönüllüyü güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Gönüllü bulunamadı")
    fun updateVolunteer(
        @Parameter(description = "Gönüllü ID", required = true)
        @PathParam("id") id: UUID,
        request: UpdateVolunteerRequest
    ): VolunteerDto {
        return volunteerService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Gönüllüyü sil (soft delete)")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Gönüllü bulunamadı")
    fun deleteVolunteer(
        @Parameter(description = "Gönüllü ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        volunteerService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Gönüllüyü kalıcı olarak sil (hard delete)",
        description = "Gönüllüyü veritabanından kalıcı olarak siler. Sadece oluşturulduktan sonraki belirli süre içinde izin verilir."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "400", description = "Hard delete izni yok - zaman aşımı")
    @APIResponse(responseCode = "404", description = "Gönüllü bulunamadı")
    fun hardDeleteVolunteer(
        @Parameter(description = "Gönüllü ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        volunteerService.hardDelete(id)
        return Response.noContent().build()
    }
}
