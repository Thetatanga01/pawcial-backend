package com.pawcial.service

import com.pawcial.dto.CreatePersonRequest
import com.pawcial.dto.PersonDto
import com.pawcial.entity.core.Person
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PersonService {

    fun findAll(): List<PersonDto> {
        return Person.findAll().list()
            .map { it.toDto() }
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
}

