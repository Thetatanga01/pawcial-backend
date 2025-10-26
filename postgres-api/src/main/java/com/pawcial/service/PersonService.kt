package com.pawcial.service

import com.pawcial.dto.CreatePersonRequest
import com.pawcial.dto.PersonDto
import com.pawcial.dto.UpdatePersonRequest
import com.pawcial.entity.core.Person
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class PersonService {

    fun findAll(all: Boolean = false): List<PersonDto> {
        return if (all) {
            Person.findAll().list().map { it.toDto() }
        } else {
            Person.find("isActive = true").list().map { it.toDto() }
        }
    }

    fun findById(id: UUID): PersonDto {
        return Person.findById(id)?.toDto()
            ?: throw NotFoundException("Person not found: $id")
    }

    @Transactional
    fun create(request: CreatePersonRequest): PersonDto {
        val person = Person().apply {
            fullName = request.fullName
            phone = request.phone
            email = request.email
            address = request.address
            notes = request.notes
            isOrganization = request.isOrganization
            organizationName = request.organizationName
            organizationType = request.organizationType
        }
        person.persist()
        return person.toDto()
    }

    @Transactional
    fun update(id: UUID, request: UpdatePersonRequest): PersonDto {
        val person = Person.findById(id)
            ?: throw NotFoundException("Person not found: $id")

        person.apply {
            request.fullName?.let { fullName = it }
            request.phone?.let { phone = it }
            request.email?.let { email = it }
            request.address?.let { address = it }
            request.notes?.let { notes = it }
            request.isOrganization?.let { isOrganization = it }
            request.organizationName?.let { organizationName = it }
            request.organizationType?.let { organizationType = it }
        }
        person.persist()
        return person.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val person = Person.findById(id)
            ?: throw NotFoundException("Person not found: $id")
        person.isActive = false
        person.persist()
    }
}
