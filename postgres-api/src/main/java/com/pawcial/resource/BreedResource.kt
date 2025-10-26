package com.pawcial.resource

import com.pawcial.dto.BreedDto
import com.pawcial.dto.CreateBreedRequest
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
    @Operation(summary = "Tüm ırkları listele", description = "Aktif veya tüm ırkları getirir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllBreeds(
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean
    ): List<BreedDto> {
        return breedService.findAll(all)
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
    @Operation(summary = "Irkı sil")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Irk bulunamadı")
    fun deleteBreed(
        @Parameter(description = "Irk ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        breedService.delete(id)
        return Response.noContent().build()
    }
}
