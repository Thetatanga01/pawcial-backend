package com.pawcial.resource

import com.pawcial.dto.CreateSpeciesRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.SpeciesDto
import com.pawcial.dto.UpdateSpeciesRequest
import com.pawcial.service.SpeciesService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/species")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Species", description = "Tür (Species) yönetimi")
class SpeciesResource {

    @Inject
    lateinit var speciesService: SpeciesService

    @GET
    @Operation(summary = "Tüm türleri listele", description = "Aktif veya tüm türleri getirir (sayfalama ile)")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllSpecies(
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<SpeciesDto> {
        return speciesService.findAll(all, page, size)
    }

    @GET
    @Path("/search")
    @Operation(summary = "Türlerde ara", description = "Bilimsel veya yaygın isimle arama")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun searchSpecies(
        @Parameter(description = "Bilimsel isim ile arama")
        @QueryParam("scientificName") scientificName: String?,
        @Parameter(description = "Yaygın isim ile arama")
        @QueryParam("commonName") commonName: String?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<SpeciesDto> {
        return speciesService.search(scientificName, commonName, all, page, size)
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "ID'ye göre tür getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Tür bulunamadı")
    fun getSpeciesById(
        @Parameter(description = "Tür ID", required = true)
        @PathParam("id") id: UUID
    ): SpeciesDto {
        return speciesService.findById(id)
    }

    @POST
    @Operation(summary = "Yeni tür ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createSpecies(request: CreateSpeciesRequest): Response {
        val created = speciesService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Türü güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Tür bulunamadı")
    fun updateSpecies(
        @Parameter(description = "Tür ID", required = true)
        @PathParam("id") id: UUID,
        request: UpdateSpeciesRequest
    ): SpeciesDto {
        return speciesService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Türü sil")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Tür bulunamadı")
    fun deleteSpecies(
        @Parameter(description = "Tür ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        speciesService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Türü kalıcı olarak sil (hard delete)",
        description = "Türü veritabanından kalıcı olarak siler. Sadece oluşturulduktan sonraki belirli süre içinde izin verilir."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "400", description = "Hard delete izni yok - zaman aşımı")
    @APIResponse(responseCode = "404", description = "Tür bulunamadı")
    fun hardDeleteSpecies(
        @Parameter(description = "Tür ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        speciesService.hardDelete(id)
        return Response.noContent().build()
    }
}
