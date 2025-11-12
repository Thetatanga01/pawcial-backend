package com.pawcial.service

import com.pawcial.dto.PagedResponse
import com.pawcial.dto.ProficiencyLevelDto
import com.pawcial.entity.dictionary.ProficiencyLevel
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class ProficiencyLevelService {

    fun findAll(all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<ProficiencyLevelDto> {
        val query = if (all) "1=1" else "isActive = true"

        // Get total count
        val totalElements = ProficiencyLevel.count(query)

        // Get paginated results ordered by display_order
        val content = ProficiencyLevel.find("$query ORDER BY displayOrder, label")
            .page(page, size)
            .list()
            .map { it.toDto() }

        val totalPages = ((totalElements + size - 1) / size).toInt()

        return PagedResponse(
            content = content,
            page = page,
            size = size,
            totalElements = totalElements,
            totalPages = totalPages,
            hasNext = page < totalPages - 1,
            hasPrevious = page > 0
        )
    }

    fun findByCode(code: String): ProficiencyLevelDto {
        return ProficiencyLevel.findById(code)?.toDto()
            ?: throw NotFoundException("Proficiency level not found: $code")
    }

    @Transactional
    fun create(request: com.pawcial.dto.CreateProficiencyLevelRequest): ProficiencyLevelDto {
        val proficiencyLevel = ProficiencyLevel().apply {
            this.code = request.code
            this.label = request.label
            this.description = request.description
            this.displayOrder = request.displayOrder
        }
        proficiencyLevel.persist()
        return proficiencyLevel.toDto()
    }

    @Transactional
    fun update(code: String, request: com.pawcial.dto.UpdateProficiencyLevelRequest): ProficiencyLevelDto {
        val proficiencyLevel = ProficiencyLevel.findById(code)
            ?: throw NotFoundException("Proficiency level not found: $code")

        proficiencyLevel.apply {
            request.label?.let { this.label = it }
            request.description?.let { this.description = it }
            request.displayOrder?.let { this.displayOrder = it }
        }
        proficiencyLevel.persist()
        return proficiencyLevel.toDto()
    }

    @Transactional
    fun toggleActive(code: String): ProficiencyLevelDto {
        val proficiencyLevel = ProficiencyLevel.findById(code)
            ?: throw NotFoundException("Proficiency level not found: $code")
        proficiencyLevel.isActive = !proficiencyLevel.isActive
        proficiencyLevel.persist()
        return proficiencyLevel.toDto()
    }

    @Transactional
    fun delete(code: String): Boolean {
        val proficiencyLevel = ProficiencyLevel.findById(code) ?: return false
        proficiencyLevel.delete()
        return true
    }
}

