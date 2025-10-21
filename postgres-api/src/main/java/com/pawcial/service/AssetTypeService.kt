package com.pawcial.service

import com.pawcial.dto.AssetTypeDto
import com.pawcial.entity.dictionary.AssetType
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AssetTypeService {

    fun findAll(): List<AssetTypeDto> {
        return AssetType.findAll().list()
            .map { it.toDto() }
    }
}

