package com.pawcial.dto

data class OrganizationDto(
    val code: String?,
    val label: String?,
    val organizationType: String?,
    val contactPhone: String?,
    val contactEmail: String?,
    val address: String?,
    val notes: String?,
    val isActive: Boolean
)

