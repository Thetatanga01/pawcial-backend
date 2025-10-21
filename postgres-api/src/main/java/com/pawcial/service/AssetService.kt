package com.pawcial.service

import com.pawcial.dto.AssetDto
import com.pawcial.entity.core.Asset
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AssetService {

    fun findAll(): List<AssetDto> {
        return Asset.findAll().list()
            .map { it.toDto() }
    }
}


