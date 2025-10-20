package com.pawcial.repository

import com.pawcial.entity.core.Animal
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class AnimalRepository : PanacheRepository<Animal> {

    fun findByName(name: String): List<Animal> {
        return list("name", name)
    }

    fun findBySpecies(speciesId: UUID): List<Animal> {
        return list("species.id", speciesId)
    }
}