package com.pawcial.service

import com.pawcial.dto.AssetStatusDto
import com.pawcial.entity.dictionary.AssetStatus
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AssetStatusService {

    fun findAll(): List<AssetStatusDto> {
        return AssetStatus.findAll().list()
            .map { it.toDto() }
    }
}
