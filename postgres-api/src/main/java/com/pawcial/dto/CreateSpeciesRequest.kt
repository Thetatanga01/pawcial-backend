package com.pawcial.dto

data class CreateSpeciesRequest(
    val scientificName: String,
    val commonName: String?,
    val domesticStatus: String?
)