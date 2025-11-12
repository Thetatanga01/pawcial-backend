package com.pawcial.dto

import java.time.LocalDate
import java.util.*

data class UpdateAssetRequest(
    val facilityId: UUID?,
    val unitId: UUID?,
    val code: String?,
    val name: String?,
    val type: String?,
    val serialNo: String?,
    val purchaseDate: LocalDate?,
    val warrantyEnd: LocalDate?,
    val status: String?
)

