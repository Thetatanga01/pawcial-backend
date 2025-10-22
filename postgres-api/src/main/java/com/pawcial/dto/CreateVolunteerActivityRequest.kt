package com.pawcial.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class CreateVolunteerActivityRequest(
    val volunteerId: UUID,
    val activityDate: LocalDate,
    val areaCode: String?,
    val hours: BigDecimal?,
    val description: String?
)

