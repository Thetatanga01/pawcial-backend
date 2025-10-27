package com.pawcial.dto

import java.util.*

data class PersonDto(
    val id: UUID?,
    val fullName: String?,
    val phone: String?,
    val email: String?,
    val address: String?,
    val notes: String?,
    val isOrganization: Boolean,
    val organization: OrganizationDto?,
    val isActive: Boolean
)

