package com.pawcial.dto

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

data class CreateAssetServiceRequest(
    val assetId: UUID,
    val serviceAt: OffsetDateTime,
    val serviceType: String?,
    val vendor: String?,
    val cost: BigDecimal?,
    val notes: String?
)


