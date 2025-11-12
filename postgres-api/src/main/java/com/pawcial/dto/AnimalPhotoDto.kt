package com.pawcial.dto

import java.time.OffsetDateTime
import java.util.*

data class AnimalPhotoDto(
    val id: UUID?,
    val animalId: UUID?,
    val photoUrl: String,
    val s3Key: String,
    val photoOrder: Int?,
    val isPrimary: Boolean,
    val description: String?,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)

