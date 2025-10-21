package com.pawcial.service

import com.pawcial.dto.PersonDto
import com.pawcial.entity.core.Person
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PersonService {

    fun findAll(): List<PersonDto> {
        return Person.findAll().list()
            .map { it.toDto() }
    }
}

