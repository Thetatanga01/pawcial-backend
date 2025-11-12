package com.pawcial.dto

data class CreatePersonRequest(
    val fullName: String,
    val phone: String?,
    val email: String?,
    val address: String?,
    val notes: String?,
    val isOrganization: Boolean = false,
    val organizationCode: String?
)

