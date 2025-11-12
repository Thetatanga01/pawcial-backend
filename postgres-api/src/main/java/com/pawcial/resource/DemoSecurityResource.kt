package com.pawcial.resource

import io.quarkus.security.Authenticated
import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import io.quarkus.security.identity.SecurityIdentity
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.logging.Logger

/**
 * Demo Resource - Keycloak Authentication örnekleri
 *
 * Bu resource farklı authentication seviyeleriyle endpoint örnekleri gösterir
 */
@Path("/api/demo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Demo", description = "Keycloak authentication demo endpoint'leri")
class DemoSecurityResource {

    @Inject
    lateinit var logger: Logger

    @Context
    lateinit var securityIdentity: SecurityIdentity

    @GET
    @Path("/public")
    @PermitAll
    @Operation(
        summary = "Public endpoint - Kimlik doğrulama gerektirmez",
        description = "Herkes erişebilir"
    )
    fun publicEndpoint(): Response {
        return Response.ok(
            mapOf(
                "message" to "This is a public endpoint",
                "authenticated" to securityIdentity.isAnonymous.not(),
                "timestamp" to System.currentTimeMillis()
            )
        ).build()
    }

    @GET
    @Path("/protected")
    @Authenticated
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(
        summary = "Protected endpoint - Kimlik doğrulama gerektirir",
        description = "Sadece authenticated kullanıcılar erişebilir"
    )
    fun protectedEndpoint(): Response {
        val username = securityIdentity.principal?.name ?: "unknown"

        logger.info("Protected endpoint accessed by: $username")

        return Response.ok(
            mapOf(
                "message" to "This is a protected endpoint",
                "user" to username,
                "roles" to securityIdentity.roles.toList(),
                "timestamp" to System.currentTimeMillis()
            )
        ).build()
    }

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(
        summary = "Admin only endpoint",
        description = "Sadece 'admin' rolüne sahip kullanıcılar erişebilir"
    )
    fun adminOnlyEndpoint(): Response {
        val username = securityIdentity.principal?.name ?: "unknown"

        logger.info("Admin endpoint accessed by: $username")

        return Response.ok(
            mapOf(
                "message" to "This is an admin-only endpoint",
                "user" to username,
                "roles" to securityIdentity.roles.toList(),
                "timestamp" to System.currentTimeMillis()
            )
        ).build()
    }

    @GET
    @Path("/moderator")
    @RolesAllowed("admin", "moderator")
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(
        summary = "Admin or Moderator endpoint",
        description = "Sadece 'admin' veya 'moderator' rolüne sahip kullanıcılar erişebilir"
    )
    fun moderatorEndpoint(): Response {
        val username = securityIdentity.principal?.name ?: "unknown"
        val roles = securityIdentity.roles.toList()

        logger.info("Moderator endpoint accessed by: $username with roles: $roles")

        return Response.ok(
            mapOf(
                "message" to "This endpoint is for admins and moderators",
                "user" to username,
                "roles" to roles,
                "timestamp" to System.currentTimeMillis()
            )
        ).build()
    }

    @GET
    @Path("/user")
    @RolesAllowed("user", "admin", "moderator")
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(
        summary = "User endpoint",
        description = "Sadece 'user', 'admin' veya 'moderator' rolüne sahip kullanıcılar erişebilir"
    )
    fun userEndpoint(): Response {
        val username = securityIdentity.principal?.name ?: "unknown"

        return Response.ok(
            mapOf(
                "message" to "This is a user endpoint",
                "user" to username,
                "roles" to securityIdentity.roles.toList(),
                "timestamp" to System.currentTimeMillis()
            )
        ).build()
    }

    @POST
    @Path("/test")
    @Authenticated
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(
        summary = "Test endpoint - POST request",
        description = "Authenticated kullanıcılar için test POST endpoint'i"
    )
    fun testPostEndpoint(body: Map<String, Any>?): Response {
        val username = securityIdentity.principal?.name ?: "unknown"

        return Response.ok(
            mapOf(
                "message" to "POST request received",
                "user" to username,
                "receivedData" to body,
                "timestamp" to System.currentTimeMillis()
            )
        ).build()
    }

    @GET
    @Path("/whoami")
    @Authenticated
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(
        summary = "Who am I - Kullanıcı detayları",
        description = "Mevcut kullanıcının tüm bilgilerini gösterir"
    )
    fun whoAmI(): Response {
        val attributes = securityIdentity.attributes

        return Response.ok(
            mapOf(
                "principal" to securityIdentity.principal?.name,
                "isAnonymous" to securityIdentity.isAnonymous,
                "roles" to securityIdentity.roles.toList(),
                "attributes" to mapOf(
                    "email" to attributes["email"],
                    "name" to attributes["name"],
                    "preferred_username" to attributes["preferred_username"],
                    "email_verified" to attributes["email_verified"],
                    "given_name" to attributes["given_name"],
                    "family_name" to attributes["family_name"]
                ),
                "timestamp" to System.currentTimeMillis()
            )
        ).build()
    }
}

