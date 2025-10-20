package com.pawcial.service
import com.pawcial.dto.*
import com.pawcial.entity.core.Animal
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class AnimalService {

    fun findAll(speciesId: UUID?, status: String?): List<AnimalDto> {
        var query = "1=1"
        val params = mutableMapOf<String, Any>()

        if (speciesId != null) {
            query += " and species.id = :speciesId"
            params["speciesId"] = speciesId
        }

        if (status != null) {
            query += " and status = :status"
            params["status"] = status
        }

        return Animal.find(query, params)
            .list()
            .map { it.toDto() }
    }

    fun findById(id: UUID): AnimalDto {
        return Animal.findById(id)?.toDto()
            ?: throw NotFoundException("Animal not found: $id")
    }

    @Transactional
    fun create(request: CreateAnimalRequest): AnimalDto {
        val animal = Animal().apply {
            name = request.name
            sex = request.sex
            // ... diğer alanlar
        }
        animal.persist()
        return animal.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdateAnimalRequest): AnimalDto {
        val animal = Animal.findById(id)
            ?: throw NotFoundException("Animal not found: $id")

        animal.apply {
            name = request.name ?: name
            sex = request.sex ?: sex
            // ... diğer alanlar
        }
        return animal.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val deleted = Animal.deleteById(id)
        if (!deleted) {
            throw NotFoundException("Animal not found: $id")
        }
    }
}
