package com.pawcial.service

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import java.io.InputStream
import java.util.*

@ApplicationScoped
class S3Service {

    @ConfigProperty(name = "aws.s3.bucket-name")
    lateinit var bucketName: String

    @ConfigProperty(name = "aws.s3.region")
    lateinit var region: String

    @ConfigProperty(name = "aws.access.key.id")
    lateinit var accessKeyId: String

    @ConfigProperty(name = "aws.secret.access.key")
    lateinit var secretAccessKey: String

    private val s3Client: S3Client by lazy {
        S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKeyId, secretAccessKey)
                )
            )
            .build()
    }

    /**
     * Upload file to S3
     * @param animalId Animal UUID
     * @param photoNumber Photo number (1, 2, 3, ...)
     * @param inputStream File input stream
     * @param contentType File content type (image/jpeg, image/png)
     * @param contentLength File size
     * @return S3 key (path in bucket)
     */
    fun uploadAnimalPhoto(
        animalId: UUID,
        photoNumber: Int,
        inputStream: InputStream,
        contentType: String,
        contentLength: Long
    ): String {
        val s3Key = "$animalId/$photoNumber"

        val putRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(s3Key)
            .contentType(contentType)
            .contentLength(contentLength)
            .build()

        s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, contentLength))

        return s3Key
    }

    /**
     * Get public URL for S3 object
     * @param s3Key S3 object key
     * @return Public URL
     */
    fun getPublicUrl(s3Key: String): String {
        return "https://$bucketName.s3.$region.amazonaws.com/$s3Key"
    }

    /**
     * Generate pre-signed URL for temporary access (more secure)
     * @param s3Key S3 object key
     * @param expirationMinutes URL expiration time in minutes (default 60)
     * @return Pre-signed URL
     */
    fun generatePresignedUrl(s3Key: String, expirationMinutes: Long = 60): String {
        val getObjectRequest = software.amazon.awssdk.services.s3.model.GetObjectRequest.builder()
            .bucket(bucketName)
            .key(s3Key)
            .build()

        val presignRequest = software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest.builder()
            .signatureDuration(java.time.Duration.ofMinutes(expirationMinutes))
            .getObjectRequest(getObjectRequest)
            .build()

        val presigner = software.amazon.awssdk.services.s3.presigner.S3Presigner.builder()
            .region(software.amazon.awssdk.regions.Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKeyId, secretAccessKey)
                )
            )
            .build()

        val presignedRequest = presigner.presignGetObject(presignRequest)
        presigner.close()

        return presignedRequest.url().toString()
    }

    /**
     * Delete object from S3
     * @param s3Key S3 object key
     */
    fun deleteObject(s3Key: String) {
        val deleteRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(s3Key)
            .build()

        s3Client.deleteObject(deleteRequest)
    }

    /**
     * Check if object exists in S3
     * @param s3Key S3 object key
     * @return true if exists
     */
    fun objectExists(s3Key: String): Boolean {
        return try {
            val headRequest = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build()

            s3Client.headObject(headRequest)
            true
        } catch (e: NoSuchKeyException) {
            false
        }
    }

    /**
     * Get next available photo number for an animal
     * @param animalId Animal UUID
     * @return Next photo number
     */
    fun getNextPhotoNumber(animalId: UUID): Int {
        val prefix = "$animalId/"

        val listRequest = ListObjectsV2Request.builder()
            .bucket(bucketName)
            .prefix(prefix)
            .build()

        val response = s3Client.listObjectsV2(listRequest)

        if (response.contents().isEmpty()) {
            return 1
        }

        val existingNumbers = response.contents()
            .mapNotNull { obj ->
                obj.key().removePrefix(prefix).toIntOrNull()
            }

        return (existingNumbers.maxOrNull() ?: 0) + 1
    }
}

