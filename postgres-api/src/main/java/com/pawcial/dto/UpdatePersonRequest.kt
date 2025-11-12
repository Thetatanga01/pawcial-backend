package com.pawcial.dto

data class UpdatePersonRequest(
    val fullName: String?,
    val phone: String?,
    val email: String?,
    val address: String?,
    val notes: String?,
    val isOrganization: Boolean?,
    val organizationCode: String?
)

