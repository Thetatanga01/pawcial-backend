package com.pawcial.dto

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

data class AssetDto(
    val id: UUID?,
    val facilityId: UUID?,
    val facilityName: String?,
    val unitId: UUID?,
    val unitCode: String?,
    val code: String?,
    val name: String?,
    val type: String?,
    val serialNo: String?,
    val purchaseDate: LocalDate?,
    val warrantyEnd: LocalDate?,
    val status: String?,
    val isActive: Boolean,
    val createdAt: OffsetDateTime?,
    val updatedAt: OffsetDateTime?
)

