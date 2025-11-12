package com.pawcial.resource

import com.pawcial.dto.BreedDto
import com.pawcial.dto.CreateBreedRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.UpdateBreedRequest
import com.pawcial.service.BreedService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/breeds")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Breeds", description = "Irk (Breed) yönetimi")
class BreedResource {

    @Inject
    lateinit var breedService: BreedService

    @GET
    @Operation(summary = "Tüm ırkları listele", description = "Aktif veya tüm ırkları getirir (sayfalama ile)")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllBreeds(
        @Parameter(description = "Türe göre filtrele")
        @QueryParam("species") speciesId: UUID?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<BreedDto> {
        return breedService.findAll(speciesId, all, page, size)
    }

    @GET
    @Path("/search")
    @Operation(summary = "Irklarda ara", description = "Irk ismi veya tür ismi ile arama")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun searchBreeds(
        @Parameter(description = "Irk ismi ile arama")
        @QueryParam("name") name: String?,
        @Parameter(description = "Tür ismi ile arama")
        @QueryParam("speciesName") speciesName: String?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<BreedDto> {
        return breedService.search(name, speciesName, all, page, size)
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "ID'ye göre ırk getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Irk bulunamadı")
    fun getBreedById(
        @Parameter(description = "Irk ID", required = true)
        @PathParam("id") id: UUID
    ): BreedDto {
        return breedService.findById(id)
    }

    @POST
    @Operation(summary = "Yeni ırk ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createBreed(request: CreateBreedRequest): Response {
        val created = breedService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Irkı güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Irk bulunamadı")
    fun updateBreed(
        @Parameter(description = "Irk ID", required = true)
        @PathParam("id") id: UUID,
        request: UpdateBreedRequest
    ): BreedDto {
        return breedService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Irkı sil (soft delete)")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Irk bulunamadı")
    fun deleteBreed(
        @Parameter(description = "Irk ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        breedService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Irkı kalıcı olarak sil (hard delete)",
        description = "Irkı veritabanından kalıcı olarak siler. Sadece oluşturulduktan sonraki belirli süre içinde izin verilir."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "400", description = "Hard delete izni yok - zaman aşımı")
    @APIResponse(responseCode = "404", description = "Irk bulunamadı")
    fun hardDeleteBreed(
        @Parameter(description = "Irk ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        breedService.hardDelete(id)
        return Response.noContent().build()
    }
}
