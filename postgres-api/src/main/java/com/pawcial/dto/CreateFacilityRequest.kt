package com.pawcial.dto

data class CreateFacilityRequest(
    val name: String,
    val type: String?,
    val country: String?,
    val city: String?,
    val address: String?
)

