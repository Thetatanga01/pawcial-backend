package com.pawcial.entity.core.data

import java.io.Serializable
import java.util.UUID

data class VolunteerAreaId(
    var volunteer: UUID? = null,
    var areaCode: String? = null
) : Serializable