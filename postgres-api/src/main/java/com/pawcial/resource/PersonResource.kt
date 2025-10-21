package com.pawcial.resource

import com.pawcial.dto.PersonDto
import com.pawcial.service.PersonService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/api/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PersonResource {

    @Inject
    lateinit var personService: PersonService

    @GET
    fun getAllPersons(): List<PersonDto> {
        return personService.findAll()
    }
}

