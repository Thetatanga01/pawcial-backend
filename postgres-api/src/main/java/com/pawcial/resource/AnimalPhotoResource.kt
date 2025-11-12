package com.pawcial.resource

import com.pawcial.dto.AnimalPhotoDto
import com.pawcial.service.AnimalPhotoService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.io.ByteArrayInputStream
import java.util.*

@Path("/api/animals/{animalId}/photos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Animal Photos", description = "Hayvan fotoğraf yönetimi (S3)")
class AnimalPhotoResource {

    @Inject
    lateinit var animalPhotoService: AnimalPhotoService

    @GET
    @Operation(summary = "Hayvanın fotoğraflarını listele", description = "Belirtilen hayvana ait tüm fotoğrafları getirir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    fun getAnimalPhotos(
        @Parameter(description = "Hayvan ID", required = true)
        @PathParam("animalId") animalId: UUID
    ): List<AnimalPhotoDto> {
        return animalPhotoService.getAnimalPhotos(animalId)
    }

    @POST
    @Operation(summary = "Fotoğraf yükle", description = "Hayvana fotoğraf yükler (S3'e) - base64 encoded")
    @APIResponse(responseCode = "201", description = "Fotoğraf başarıyla yüklendi")
    @APIResponse(responseCode = "404", description = "Hayvan bulunamadı")
    fun uploadPhoto(
        @Parameter(description = "Hayvan ID", required = true)
        @PathParam("animalId") animalId: UUID,
        request: PhotoUploadRequest
    ): Response {
        val imageBytes = java.util.Base64.getDecoder().decode(request.imageBase64)
        val inputStream = ByteArrayInputStream(imageBytes)

        val photo = animalPhotoService.uploadPhoto(
            animalId = animalId,
            inputStream = inputStream,
            contentType = request.contentType ?: "image/jpeg",
            contentLength = imageBytes.size.toLong(),
            description = request.description,
            setAsPrimary = request.setAsPrimary ?: false
        )

        return Response.status(Response.Status.CREATED).entity(photo).build()
    }

    @DELETE
    @Path("/{photoId}")
    @Operation(summary = "Fotoğraf sil", description = "Fotoğrafı S3'ten ve veritabanından siler")
    @APIResponse(responseCode = "204", description = "Başarıyla silindi")
    @APIResponse(responseCode = "404", description = "Fotoğraf bulunamadı")
    fun deletePhoto(
        @Parameter(description = "Hayvan ID", required = true)
        @PathParam("animalId") animalId: UUID,
        @Parameter(description = "Fotoğraf ID", required = true)
        @PathParam("photoId") photoId: UUID
    ): Response {
        val deleted = animalPhotoService.deletePhoto(photoId)
        return if (deleted) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @PATCH
    @Path("/{photoId}/set-primary")
    @Operation(summary = "Ana fotoğraf olarak ayarla", description = "Belirtilen fotoğrafı ana fotoğraf yapar")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Fotoğraf bulunamadı")
    fun setPrimaryPhoto(
        @Parameter(description = "Hayvan ID", required = true)
        @PathParam("animalId") animalId: UUID,
        @Parameter(description = "Fotoğraf ID", required = true)
        @PathParam("photoId") photoId: UUID
    ): AnimalPhotoDto {
        return animalPhotoService.setPrimaryPhoto(photoId)
    }

    @PATCH
    @Path("/{photoId}/order")
    @Operation(summary = "Fotoğraf sırasını güncelle", description = "Fotoğrafın görüntülenme sırasını değiştirir")
    @APIResponse(responseCode = "200", description = "Başarılı")
    @APIResponse(responseCode = "404", description = "Fotoğraf bulunamadı")
    fun updatePhotoOrder(
        @Parameter(description = "Hayvan ID", required = true)
        @PathParam("animalId") animalId: UUID,
        @Parameter(description = "Fotoğraf ID", required = true)
        @PathParam("photoId") photoId: UUID,
        @Parameter(description = "Yeni sıra", required = true)
        @QueryParam("order") order: Int
    ): AnimalPhotoDto {
        return animalPhotoService.updatePhotoOrder(photoId, order)
    }
}

data class PhotoUploadRequest(
    val imageBase64: String,
    val contentType: String? = "image/jpeg",
    val description: String? = null,
    val setAsPrimary: Boolean? = false
)

