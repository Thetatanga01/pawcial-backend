package com.pawcial.service

import com.pawcial.dto.CreatePersonRequest
import com.pawcial.dto.PagedResponse
import com.pawcial.dto.PersonDto
import com.pawcial.dto.*
import com.pawcial.entity.core.Person
import com.pawcial.entity.dictionary.Organization
import com.pawcial.extension.toDto
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.NotFoundException
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class PersonService {

    @Inject
    lateinit var systemParameterService: SystemParameterService

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
            fullName = request.fullName.takeIf { it.isNotBlank() }
            phone = request.phone?.takeIf { it.isNotBlank() }
            email = request.email?.takeIf { it.isNotBlank() }
            address = request.address?.takeIf { it.isNotBlank() }
            notes = request.notes?.takeIf { it.isNotBlank() }
            isOrganization = request.isOrganization
            organization = request.organizationCode?.takeIf { it.isNotBlank() }?.let {
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
            request.fullName?.let { fullName = it.takeIf { it.isNotBlank() } }
            request.phone?.let { phone = it.takeIf { it.isNotBlank() } }
            request.email?.let { email = it.takeIf { it.isNotBlank() } }
            request.address?.let { address = it.takeIf { it.isNotBlank() } }
            request.notes?.let { notes = it.takeIf { it.isNotBlank() } }
            request.isOrganization?.let { isOrganization = it }
            request.organizationCode?.let { orgCode ->
                if (orgCode.isNotBlank()) {
                    organization = Organization.findById(orgCode)
                        ?: throw NotFoundException("Organization not found: $orgCode")
                } else {
                    organization = null
                }
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

    @Transactional
    fun hardDelete(id: UUID) {
        val person = Person.findById(id)
            ?: throw NotFoundException("Person not found: $id")

        val hardDeleteWindowSeconds = systemParameterService.getHardDeleteWindowSeconds()
        val now = OffsetDateTime.now()
        val createdAt = person.createdAt ?: throw IllegalStateException("Person has no creation timestamp")
        val elapsedSeconds = java.time.Duration.between(createdAt, now).seconds

        if (elapsedSeconds > hardDeleteWindowSeconds) {
            throw BadRequestException(
                "Hard delete not allowed: Record is older than $hardDeleteWindowSeconds seconds " +
                "(created ${elapsedSeconds} seconds ago). Only soft delete is available."
            )
        }

        person.delete()
    }
}
