package com.pawcial.dto

data class UpdateSpeciesRequest(
    val scientificName: String?,
    val commonName: String?,
    val domesticStatus: String?
)

