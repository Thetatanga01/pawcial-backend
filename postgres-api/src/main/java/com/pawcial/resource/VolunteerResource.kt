package com.pawcial.resource

import com.pawcial.dto.CreateVolunteerRequest
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
    @Operation(summary = "Tüm gönüllüleri listele")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllVolunteers(): List<VolunteerDto> {
        return volunteerService.findAll()
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
    @Operation(summary = "Gönüllüyü sil")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Gönüllü bulunamadı")
    fun deleteVolunteer(
        @Parameter(description = "Gönüllü ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        volunteerService.delete(id)
        return Response.noContent().build()
    }
}
