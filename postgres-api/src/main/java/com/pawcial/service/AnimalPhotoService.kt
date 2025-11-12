package com.pawcial.service

import com.pawcial.dto.AnimalPhotoDto
import com.pawcial.entity.core.Animal
import com.pawcial.entity.core.AnimalPhoto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.io.InputStream
import java.util.*

@ApplicationScoped
class AnimalPhotoService {

    @Inject
    lateinit var s3Service: S3Service

    fun getAnimalPhotos(animalId: UUID): List<AnimalPhotoDto> {
        return AnimalPhoto.find("animal.id = ?1 and isActive = true order by photoOrder, createdAt", animalId)
            .list()
            .map { it.toDto() }
    }

    @Transactional
    fun uploadPhoto(
        animalId: UUID,
        inputStream: InputStream,
        contentType: String,
        contentLength: Long,
        description: String? = null,
        setAsPrimary: Boolean = false
    ): AnimalPhotoDto {
        // Check if animal exists
        val animal = Animal.findById(animalId)
            ?: throw NotFoundException("Animal not found: $animalId")

        // Get next photo number
        val photoNumber = s3Service.getNextPhotoNumber(animalId)

        // Upload to S3
        val s3Key = s3Service.uploadAnimalPhoto(animalId, photoNumber, inputStream, contentType, contentLength)
        val photoUrl = s3Service.getPublicUrl(s3Key)

        // If setting as primary, unset other primary photos
        if (setAsPrimary) {
            AnimalPhoto.update("isPrimary = false where animal.id = ?1", animalId)
        }

        // Create database record
        val animalPhoto = AnimalPhoto().apply {
            this.animal = animal
            this.photoUrl = photoUrl
            this.s3Key = s3Key
            this.photoOrder = photoNumber
            this.isPrimary = setAsPrimary
            this.description = description
        }
        animalPhoto.persist()

        return animalPhoto.toDto()
    }

    @Transactional
    fun deletePhoto(photoId: UUID): Boolean {
        val photo = AnimalPhoto.findById(photoId) ?: return false

        // Delete from S3
        photo.s3Key?.let { s3Service.deleteObject(it) }

        // Delete from database
        photo.delete()

        return true
    }

    @Transactional
    fun setPrimaryPhoto(photoId: UUID): AnimalPhotoDto {
        val photo = AnimalPhoto.findById(photoId)
            ?: throw NotFoundException("Photo not found: $photoId")

        // Unset all primary photos for this animal
        AnimalPhoto.update("isPrimary = false where animal.id = ?1", photo.animal?.id as Any)

        // Set this photo as primary
        photo.isPrimary = true
        photo.persist()

        return photo.toDto()
    }

    @Transactional
    fun updatePhotoOrder(photoId: UUID, newOrder: Int): AnimalPhotoDto {
        val photo = AnimalPhoto.findById(photoId)
            ?: throw NotFoundException("Photo not found: $photoId")

        photo.photoOrder = newOrder
        photo.persist()

        return photo.toDto()
    }

    private fun AnimalPhoto.toDto(): AnimalPhotoDto {
        // Generate pre-signed URL for secure access (expires in 60 minutes)
        val presignedUrl = try {
            s3Service.generatePresignedUrl(this.s3Key!!, 60)
        } catch (e: Exception) {
            // Fallback to public URL if presigned URL generation fails
            this.photoUrl!!
        }

        return AnimalPhotoDto(
            id = this.id,
            animalId = this.animal?.id,
            photoUrl = presignedUrl,
            s3Key = this.s3Key!!,
            photoOrder = this.photoOrder,
            isPrimary = this.isPrimary,
            description = this.description,
            isActive = this.isActive,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}

