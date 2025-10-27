package com.pawcial.service

import com.pawcial.dto.CreatePersonRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.PersonDto
import com.pawcial.dto.UpdatePersonRequest
import com.pawcial.entity.core.Person
import com.pawcial.entity.dictionary.Organization
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import java.util.*

@ApplicationScoped
class PersonService {

    fun findAll(all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<PersonDto> {
        val query = if (all) "1=1" else "isActive = true"

        // Get total count
        val totalElements = Person.count(query)

        // Get paginated results
        val content = if (all) {
            Person.findAll().page(page, size).list().map { it.toDto() }
        } else {
            Person.find("isActive = true").page(page, size).list().map { it.toDto() }
        }

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

    fun search(fullName: String?, phone: String?, email: String?, all: Boolean = false, page: Int = 0, size: Int = 20): PagedResponse<PersonDto> {
        var query = if (all) "1=1" else "isActive = true"
        val params = mutableMapOf<String, Any>()

        if (!fullName.isNullOrBlank()) {
            query += " and lower(fullName) like lower(:fullName)"
            params["fullName"] = "%$fullName%"
        }

        if (!phone.isNullOrBlank()) {
            query += " and lower(phone) like lower(:phone)"
            params["phone"] = "%$phone%"
        }

        if (!email.isNullOrBlank()) {
            query += " and lower(email) like lower(:email)"
            params["email"] = "%$email%"
        }

        // Get total count
        val totalElements = Person.count(query, params)

        // Get paginated results
        val content = Person.find(query, params)
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
            organization = request.organizationCode?.let {
                Organization.findById(it)
                    ?: throw NotFoundException("Organization not found: $it")
            }
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
            request.organizationCode?.let { orgCode ->
                organization = Organization.findById(orgCode)
                    ?: throw NotFoundException("Organization not found: $orgCode")
            }
        }
        person.persist()
        return person.toDto()
    }

    @Transactional
    fun delete(id: UUID) {
        val person = Person.findById(id)
            ?: throw NotFoundException("Person not found: $id")
        person.isActive = !person.isActive
        person.persist()
    }
}
