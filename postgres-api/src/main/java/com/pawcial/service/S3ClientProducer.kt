package com.pawcial.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@ApplicationScoped
class S3ClientProducer {

    @ConfigProperty(name = "aws.s3.region", defaultValue = "eu-central-1")
    lateinit var region: String

    @ConfigProperty(name = "aws.access.key.id")
    var accessKeyId: String? = null

    @ConfigProperty(name = "aws.secret.access.key")
    var secretAccessKey: String? = null

    private var _s3Client: S3Client? = null

    @Produces
    @ApplicationScoped
    fun produceS3Client(): S3Client {
        if (_s3Client == null) {
            val credentialsProvider: AwsCredentialsProvider = if (accessKeyId != null && secretAccessKey != null) {
                StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey))
            } else {
                DefaultCredentialsProvider.create()
            }

            _s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build()
        }
        return _s3Client!!
    }

    val s3Client: S3Client
        get() = _s3Client ?: produceS3Client()
}

