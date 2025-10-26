package com.pawcial.service
import com.pawcial.dto.*
import com.pawcial.entity.core.Animal
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import org.jboss.logging.Logger
import java.util.*

@ApplicationScoped
class AnimalService {

    @Inject
    lateinit var entityManager: EntityManager

    private val log = Logger.getLogger(AnimalService::class.java)

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
        val species = com.pawcial.entity.core.Species.findById(request.speciesId)
            ?: throw NotFoundException("Species not found: ${request.speciesId}")

        val breed = request.breedId?.let {
            com.pawcial.entity.core.Breed.findById(it)
                ?: throw NotFoundException("Breed not found: $it")
        }

        val animal = Animal().apply {
            this.species = species
            this.breed = breed
            name = request.name
            sex = request.sex
            birthDate = request.birthDate
            ageMonthsEst = request.ageMonthsEst
            size = request.size
            color = request.color
            trainingLevel = request.trainingLevel
            sterilized = request.sterilized
            isMixed = request.isMixed
            originNote = request.originNote
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
            animal.species = com.pawcial.entity.core.Species.findById(it)
                ?: throw NotFoundException("Species not found: $it")
        }

        request.breedId?.let {
            animal.breed = com.pawcial.entity.core.Breed.findById(it)
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
            request.name?.let { name = it }
            request.sex?.let { sex = it }
            request.birthDate?.let { birthDate = it }
            request.ageMonthsEst?.let { ageMonthsEst = it }
            request.size?.let { size = it }
            request.color?.let { color = it }
            request.trainingLevel?.let { trainingLevel = it }
            request.sterilized?.let { sterilized = it }
            request.isMixed?.let { isMixed = it }
            request.originNote?.let { originNote = it }
            request.currentUnitId?.let { currentUnitId = it }
            request.currentSince?.let { currentSince = it }
        }
        animal.persist()
        entityManager.flush()
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
