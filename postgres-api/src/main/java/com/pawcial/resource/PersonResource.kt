package com.pawcial.resource

import com.pawcial.dto.CreatePersonRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.PersonDto
import com.pawcial.dto.UpdatePersonRequest
import com.pawcial.service.PersonService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Persons", description = "Kişi/Kurum (Person) yönetimi")
class PersonResource {

    @Inject
    lateinit var personService: PersonService

    @GET
    @Operation(summary = "Tüm kişileri listele", description = "Aktif veya tüm kişileri getirir (sayfalama ile)")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAllPersons(
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<PersonDto> {
        return personService.findAll(all, page, size)
    }

    @GET
    @Path("/search")
    @Operation(summary = "Kişilerde ara", description = "İsim, telefon veya email'e göre arama")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun searchPersons(
        @Parameter(description = "İsim ile arama")
        @QueryParam("fullName") fullName: String?,
        @Parameter(description = "Telefon ile arama")
        @QueryParam("phone") phone: String?,
        @Parameter(description = "Email ile arama")
        @QueryParam("email") email: String?,
        @Parameter(description = "Tüm kayıtları getir (aktif olmayanlar dahil)")
        @QueryParam("all") @DefaultValue("false") all: Boolean,
        @Parameter(description = "Sayfa numarası (0'dan başlar)")
        @QueryParam("page") @DefaultValue("0") page: Int,
        @Parameter(description = "Sayfa boyutu")
        @QueryParam("size") @DefaultValue("20") size: Int
    ): PagedResponse<PersonDto> {
        return personService.search(fullName, phone, email, all, page, size)
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "ID'ye göre kişi getir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Kişi bulunamadı")
    fun getPersonById(
        @Parameter(description = "Kişi ID", required = true)
        @PathParam("id") id: UUID
    ): PersonDto {
        return personService.findById(id)
    }

    @POST
    @Operation(summary = "Yeni kişi ekle")
    @APIResponse(responseCode = "201", description = "Başarıyla oluşturuldu")
    fun createPerson(request: CreatePersonRequest): Response {
        val created = personService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Kişiyi güncelle")
    @APIResponse(responseCode = "200", description = "Başarıyla güncellendi")
    @APIResponse(responseCode = "404", description = "Kişi bulunamadı")
    fun updatePerson(
        @Parameter(description = "Kişi ID", required = true)
        @PathParam("id") id: UUID,
        request: UpdatePersonRequest
    ): PersonDto {
        return personService.update(id, request)
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Kişiyi sil (soft delete)")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Kişi bulunamadı")
    fun deletePerson(
        @Parameter(description = "Kişi ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        personService.delete(id)
        return Response.noContent().build()
    }

    @DELETE
    @Path("/{id}/hard-delete")
    @Operation(
        summary = "Kişiyi kalıcı olarak sil (hard delete)",
        description = "Kişiyi veritabanından kalıcı olarak siler. Sadece oluşturulduktan sonraki belirli süre içinde izin verilir."
    )
    @APIResponse(responseCode = "204", description = "Kalıcı olarak silindi")
    @APIResponse(responseCode = "400", description = "Hard delete izni yok - zaman aşımı")
    @APIResponse(responseCode = "404", description = "Kişi bulunamadı")
    fun hardDeletePerson(
        @Parameter(description = "Kişi ID", required = true)
        @PathParam("id") id: UUID
    ): Response {
        personService.hardDelete(id)
        return Response.noContent().build()
    }
}
