package com.pawcial.resource

import com.pawcial.dto.CreatePersonRequest
import com.pawcial.dto.PersonDto
import com.pawcial.service.PersonService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

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

    @POST
    fun createPerson(request: CreatePersonRequest): Response {
        val created = personService.create(request)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }
}

