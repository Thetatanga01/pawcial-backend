package com.pawcial.service

import com.pawcial.dto.*
import com.pawcial.entity.core.*
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import org.jboss.logging.Logger
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class AnimalService {

    @Inject
    lateinit var entityManager: EntityManager

    @Inject
    lateinit var systemParameterService: SystemParameterService

    private val log = Logger.getLogger(AnimalService::class.java)

    fun findAll(speciesId: UUID?, status: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<AnimalDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (speciesId != null) {
            query += " and species.id = :speciesId"
            params["speciesId"] = speciesId
        }

        if (status != null) {
            query += " and status = :status"
            params["status"] = status
        }

        val totalElements = Animal.count(query, params)

        val content = Animal.find("$query ORDER BY name ASC", params)
            .page(page, size)
            .list()
            .map { (it as Animal).toDto() }

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

    fun findById(id: UUID): AnimalDto {
        return Animal.findById(id)?.toDto()
            ?: throw NotFoundException("Animal not found: $id")
    }

    fun search(name: String?, speciesName: String?, breedName: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<AnimalDto> {
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

        if (!breedName.isNullOrBlank()) {
            query += " and lower(breed.name) like lower(:breedName)"
            params["breedName"] = "%$breedName%"
        }

        val totalElements = Animal.count(query, params)

        val content = Animal.find("$query ORDER BY name ASC", params)
            .page(page, size)
            .list()
            .map { (it as Animal).toDto() }

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

    @Transactional
    fun create(request: CreateAnimalRequest): AnimalDto {
        val species = Species.findById(request.speciesId)
            ?: throw NotFoundException("Species not found: ${request.speciesId}")

        val breed = request.breedId?.let {
            Breed.findById(it)
                ?: throw NotFoundException("Breed not found: $it")
        }

        val animal = Animal().apply {
            this.species = species
            this.breed = breed
            name = request.name?.takeIf { it.isNotBlank() }
            sex = request.sex?.takeIf { it.isNotBlank() }
            birthDate = request.birthDate
            ageMonthsEst = request.ageMonthsEst
            size = request.size?.takeIf { it.isNotBlank() }
            color = request.color?.takeIf { it.isNotBlank() }
            leashBehavior = request.leashBehavior?.takeIf { it.isNotBlank() }
            trainingLevel = request.trainingLevel?.takeIf { it.isNotBlank() }
            sterilized = request.sterilized
            isMixed = request.isMixed
            originNote = request.originNote?.takeIf { it.isNotBlank() }
            currentUnitId = request.currentUnitId
            currentSince = request.currentSince
        }

        // Handle temperaments
        log.info("Processing temperamentCodes: ${request.temperamentCodes}")
        request.temperamentCodes?.forEach { code ->
            log.info("Adding temperament: $code")
            val temperament = com.pawcial.entity.dictionary.Temperament.findById(code)
                ?: throw NotFoundException("Temperament not found: $code")
            animal.temperaments.add(temperament)
        }
        log.info("Total temperaments added: ${animal.temperaments.size}")

        // Handle health flags
        log.info("Processing healthFlagCodes: ${request.healthFlagCodes}")
        request.healthFlagCodes?.forEach { code ->
            log.info("Adding health flag: $code")
            val healthFlag = com.pawcial.entity.dictionary.HealthFlag.findById(code)
                ?: throw NotFoundException("HealthFlag not found: $code")
            animal.healthFlags.add(healthFlag)
        }
        log.info("Total health flags added: ${animal.healthFlags.size}")

        animal.persist()
        log.info("Animal persisted with ID: ${animal.id}")
        entityManager.flush()
        log.info("EntityManager flushed")
        return animal.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdateAnimalRequest): AnimalDto {
        val animal = Animal.findById(id)
            ?: throw NotFoundException("Animal not found: $id")

        request.speciesId?.let {
            animal.species = Species.findById(it)
                ?: throw NotFoundException("Species not found: $it")
        }

        request.breedId?.let {
            animal.breed = Breed.findById(it)
                ?: throw NotFoundException("Breed not found: $it")
        }

        // Handle temperaments update
        request.temperamentCodes?.let { codes ->
            animal.temperaments.clear()
            codes.forEach { code ->
                val temperament = com.pawcial.entity.dictionary.Temperament.findById(code)
                    ?: throw NotFoundException("Temperament not found: $code")
                animal.temperaments.add(temperament)
            }
        }

        // Handle health flags update
        request.healthFlagCodes?.let { codes ->
            animal.healthFlags.clear()
            codes.forEach { code ->
                val healthFlag = com.pawcial.entity.dictionary.HealthFlag.findById(code)
                    ?: throw NotFoundException("HealthFlag not found: $code")
                animal.healthFlags.add(healthFlag)
            }
        }

        animal.apply {
            request.name?.let { name = it.takeIf { it.isNotBlank() } }
            request.sex?.let { sex = it.takeIf { it.isNotBlank() } }
            request.birthDate?.let { birthDate = it }
            request.ageMonthsEst?.let { ageMonthsEst = it }
            request.size?.let { size = it.takeIf { it.isNotBlank() } }
            request.color?.let { color = it.takeIf { it.isNotBlank() } }
            request.leashBehavior?.let { leashBehavior = it.takeIf { it.isNotBlank() } }
            request.trainingLevel?.let { trainingLevel = it.takeIf { it.isNotBlank() } }
            request.sterilized?.let { sterilized = it }
            request.isMixed?.let { isMixed = it }
            request.originNote?.let { originNote = it.takeIf { it.isNotBlank() } }
            request.currentUnitId?.let { currentUnitId = it }
            request.currentSince?.let { currentSince = it }
        }
        animal.persist()
        entityManager.flush()
        return animal.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val animal = Animal.findById(id)
            ?: throw NotFoundException("Animal not found: $id")
        animal.isActive = !animal.isActive
        animal.persist()
    }

    @Transactional
    fun hardDelete(id: UUID) {
        val animal = Animal.findById(id)
            ?: throw NotFoundException("Animal not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = animal.createdAt ?: throw IllegalStateException("Animal has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        animal.delete()
    }
}
