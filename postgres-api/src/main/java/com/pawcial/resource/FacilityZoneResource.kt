package com.pawcial.resource

import com.pawcial.dto.CreateFacilityZoneRequest
import com.pawcial.dto.FacilityZoneDto
import com.pawcial.dto.UpdateFacilityZoneRequest
import com.pawcial.service.FacilityZoneService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/facility-zones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Facility Zones", description = "Tesis Bölgesi (Facility Zone) yönetimi")
class FacilityZoneResource {

    @Inject
    lateinit var facilityZoneService: FacilityZoneService

    @GET
    @Operation(summary = "Tüm tesis bölgelerini listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllFacilityZones(): List<FacilityZoneDto> {
        return facilityZoneService.findAll()
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "ID'ye göre tesis bölgesi getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Tesis bölgesi bulunamadı")
    fun getFacilityZoneById(
        @Parameter(description = "Tesis bölgesi ID", required = true)
        @PathParam("id") id: UUID
    ): FacilityZoneDto {
        return facilityZoneService.findById(id)
    }

    @POST
    @Operation(summary = "Yeni tesis bölgesi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createFacilityZone(request: CreateFacilityZoneRequest): Response {
        val created = facilityZoneService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Tesis bölgesini güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Tesis bölgesi bulunamadı")
    fun updateFacilityZone(
        @Parameter(description = "Tesis bölgesi ID", required = true)
        @PathParam("id") id: UUID,
        request: UpdateFacilityZoneRequest
    ): FacilityZoneDto {
        return facilityZoneService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Tesis bölgesini sil")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Tesis bölgesi bulunamadı")
    fun deleteFacilityZone(
        @Parameter(description = "Tesis bölgesi ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        facilityZoneService.delete(id)
        return Response.noContent().build()
    }
}
