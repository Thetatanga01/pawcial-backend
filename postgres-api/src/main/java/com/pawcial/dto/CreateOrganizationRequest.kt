package com.pawcial.dto

data class CreateOrganizationRequest(
    val code: String,
    val label: String,
    val organizationType: String?,
    val contactPhone: String?,
    val contactEmail: String?,
    val address: String?,
    val notes: String?
)
