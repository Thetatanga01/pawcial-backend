package com.pawcial.service

import com.pawcial.dto.BreedDto
import com.pawcial.dto.CreateBreedRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.UpdateBreedRequest
import com.pawcial.entity.core.Breed
import com.pawcial.entity.core.Species
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class BreedService {

    fun findAll(speciesId: UUID?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<BreedDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (speciesId != null) {
            query += " and species.id = :speciesId"
            params["speciesId"] = speciesId
        }

        val totalElements = Breed.count(query, params)

        val content = Breed.find(query, params)
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

    fun search(name: String?, speciesName: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<BreedDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (!name.isNullOrBlank()) {
            query += " and lower(name) like lower(:name)"
            params["name"] = "%$name%"
        }

        if (!speciesName.isNullOrBlank()) {
            query += " and lower(species.commonName) like lower(:speciesName)"
            params["speciesName"] = "%$speciesName%"
        }

        val totalElements = Breed.count(query, params)

        val content = Breed.find(query, params)
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

    fun findById(id: UUID): BreedDto {
        return Breed.findById(id)?.toDto()
            ?: throw NotFoundException("Breed not found: $id")
    }

    @Transactional
    fun create(request: CreateBreedRequest): BreedDto {
        val species = Species.findById(request.speciesId)
            ?: throw NotFoundException("Species not found: ${request.speciesId}")

        val breed = Breed().apply {
            this.species = species
            name = request.name
            origin = request.origin
        }
        breed.persist()
        return breed.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdateBreedRequest): BreedDto {
        val breed = Breed.findById(id)
            ?: throw NotFoundException("Breed not found: $id")

        request.speciesId?.let {
            breed.species = Species.findById(it)
                ?: throw NotFoundException("Species not found: $it")
        }

        breed.apply {
            request.name?.let { name = it }
            request.origin?.let { origin = it }
        }
        breed.persist()
        return breed.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val breed = Breed.findById(id)
            ?: throw NotFoundException("Breed not found: $id")
        breed.isActive = !breed.isActive
        breed.persist()
    }
}
