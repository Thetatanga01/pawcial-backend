package com.pawcial.resource

import com.pawcial.dto.PagedResponse
import com.pawcial.dto.ProficiencyLevelDto
import com.pawcial.service.ProficiencyLevelService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/proficiency-levels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Proficiency Levels", description = "Proficiency level (Yeterlilik Seviyesi) dictionary yönetimi")
class ProficiencyLevelResource {

    @Inject
    lateinit var proficiencyLevelService: ProficiencyLevelService

    @GET
    @Operation(summary = "Tüm proficiency level'ları listele", description = "Aktif veya tüm proficiency level'ları getirir (sayfalama ile)")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAll(
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("100") size: Int
    ): PagedResponse<ProficiencyLevelDto> {
        return proficiencyLevelService.findAll(all, page, size)
    }

    @GET
    @Path("/{code}")
    @Operation(summary = "Code'a göre proficiency level getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Proficiency level bulunamadı")
    fun getByCode(
        @Parameter(description = "Proficiency level code", required = true)
        @PathParam("code") code: String
    ): ProficiencyLevelDto {
        return proficiencyLevelService.findByCode(code)
    }

    @POST
    @Operation(summary = "Yeni proficiency level ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun create(request: com.pawcial.dto.CreateProficiencyLevelRequest): Response {
        val created = proficiencyLevelService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{code}")
    @Operation(summary = "Proficiency level güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Proficiency level bulunamadı")
    fun update(
        @Parameter(description = "Proficiency level code", required = true)
        @PathParam("code") code: String,
        request: com.pawcial.dto.UpdateProficiencyLevelRequest
    ): ProficiencyLevelDto {
        return proficiencyLevelService.update(code, request)
    }

    @PATCH
    @Path("/{code}/toggle")
    @Operation(summary = "Proficiency level aktif/pasif durumunu değiştir")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Proficiency level bulunamadı")
    fun toggleActive(
        @Parameter(description = "Proficiency level code", required = true)
        @PathParam("code") code: String
    ): ProficiencyLevelDto {
        return proficiencyLevelService.toggleActive(code)
    }

    @DELETE
    @Path("/{code}/hard-delete")
    @Operation(
        summary = "Proficiency level'ı kalıcı olarak sil (hard delete)",
        description = "Proficiency level'ı veritabanından kalıcı olarak siler."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "404", description = "Proficiency level bulunamadı")
    fun hardDeleteProficiencyLevel(
        @Parameter(description = "Proficiency level code", required = true)
        @PathParam("code") code: String
    ): Response {
        val deleted = proficiencyLevelService.delete(code)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }
}

