package com.pawcial.resource

import com.pawcial.dto.CreateFacilityRequest
import com.pawcial.dto.FacilityDto
import com.pawcial.dto.UpdateFacilityRequest
import com.pawcial.service.FacilityService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/facilities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Facilities", description = "Tesis (Facility) yönetimi")
class FacilityResource {

    @Inject
    lateinit var facilityService: FacilityService

    @GET
    @Operation(summary = "Tüm tesisleri listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllFacilities(): List<FacilityDto> {
        return facilityService.findAll()
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "ID'ye göre tesis getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Tesis bulunamadı")
    fun getFacilityById(
        @Parameter(description = "Tesis ID", required = true)
        @PathParam("id") id: UUID
    ): FacilityDto {
        return facilityService.findById(id)
    }

    @POST
    @Operation(summary = "Yeni tesis ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createFacility(request: CreateFacilityRequest): Response {
        val created = facilityService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Tesisi güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Tesis bulunamadı")
    fun updateFacility(
        @Parameter(description = "Tesis ID", required = true)
        @PathParam("id") id: UUID,
        request: UpdateFacilityRequest
    ): FacilityDto {
        return facilityService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Tesisi sil")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Tesis bulunamadı")
    fun deleteFacility(
        @Parameter(description = "Tesis ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        facilityService.delete(id)
        return Response.noContent().build()
    }
}
