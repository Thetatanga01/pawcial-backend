package com.pawcial.resource

import com.pawcial.config.SecurityConfig
import com.pawcial.config.UserInfo
import io.quarkus.security.Authenticated
import io.quarkus.security.identity.SecurityIdentity
import jakarta.annotation.security.PermitAll
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.logging.Logger

/**
 * Authentication ve Authorization endpointleri için Resource sınıfı
 *
 * Bu sınıf Keycloak entegrasyonu üzerinden kullanıcı kimlik doğrulama,
 * yetkilendirme ve kullanıcı bilgilerini almak için endpointler sağlar.
 */
@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Keycloak OIDC Authentication endpoints")
class AuthResource {

    @Inject
    lateinit var logger: Logger

    @Context
    lateinit var securityIdentity: SecurityIdentity


    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    lateinit var authServerUrl: String

    @ConfigProperty(name = "quarkus.oidc.client-id")
    lateinit var clientId: String

    @GET
    @Path("/me")
    @Authenticated
    @Operation(
        summary = "Get current user information",
        description = "Mevcut authenticated kullanıcının bilgilerini döner"
    )
    @SecurityRequirement(name = "SecurityScheme")
    @APIResponse(
        responseCode = "200",
        description = "User information retrieved successfully",
        content = [Content(schema = Schema(implementation = UserInfo::class))]
    )
    @APIResponse(responseCode = "401", description = "Unauthorized - Token geçersiz veya eksik")
    fun getCurrentUser(): Response {
        logger.info("Getting current user info for: ${securityIdentity.principal?.name}")

        val attributes = securityIdentity.attributes
        val userInfo = UserInfo(
            userId = securityIdentity.principal?.name ?: "anonymous",
            email = attributes["email"]?.toString(),
            name = attributes["name"]?.toString(),
            preferredUsername = attributes["preferred_username"]?.toString(),
            roles = securityIdentity.roles.toSet(),
            attributes = attributes.mapValues { it.value?.toString() }
        )

        return Response.ok(userInfo).build()
    }

    @GET
    @Path("/userinfo")
    @Authenticated
    @Operation(
        summary = "Get detailed user information",
        description = "Kullanıcının detaylı bilgilerini ve token claim'lerini döner"
    )
    @SecurityRequirement(name = "SecurityScheme")
    fun getUserInfo(): Response {
        val attributes = securityIdentity.attributes
        val roles = securityIdentity.roles

        val response = mapOf(
            "principal" to securityIdentity.principal?.name,
            "isAnonymous" to securityIdentity.isAnonymous,
            "roles" to roles,
            "attributes" to attributes.mapValues { it.value?.toString() }
        )

        return Response.ok(response).build()
    }

    @GET
    @Path("/roles")
    @Authenticated
    @Operation(
        summary = "Get user roles",
        description = "Kullanıcının Keycloak'tan gelen rollerini listeler"
    )
    @SecurityRequirement(name = "SecurityScheme")
    fun getUserRoles(): Response {
        val roles = securityIdentity.roles.toList()

        return Response.ok(mapOf("roles" to roles)).build()
    }

    @POST
    @Path("/logout")
    @Authenticated
    @Operation(
        summary = "Logout endpoint",
        description = "Kullanıcıyı sistemden çıkartır ve Keycloak logout URL'ini döner"
    )
    @SecurityRequirement(name = "SecurityScheme")
    fun logout(): Response {
        logger.info("User logout requested: ${securityIdentity.principal?.name}")

        // Keycloak logout URL'i
        val logoutUrl = "$authServerUrl/protocol/openid-connect/logout"

        return Response.ok(
            mapOf(
                "message" to "Logout successful",
                "logoutUrl" to logoutUrl
            )
        ).build()
    }

    @GET
    @Path("/config")
    @PermitAll
    @Operation(
        summary = "Get authentication configuration",
        description = "Public endpoint - Keycloak konfigürasyon bilgilerini döner (client tarafı için)"
    )
    fun getAuthConfig(): Response {
        val config = mapOf(
            "authServerUrl" to authServerUrl,
            "clientId" to clientId,
            "realm" to authServerUrl.substringAfterLast("/realms/"),
            "loginUrl" to "$authServerUrl/protocol/openid-connect/auth",
            "tokenUrl" to "$authServerUrl/protocol/openid-connect/token",
            "logoutUrl" to "$authServerUrl/protocol/openid-connect/logout",
            "userInfoUrl" to "$authServerUrl/protocol/openid-connect/userinfo"
        )

        return Response.ok(config).build()
    }

    @GET
    @Path("/health")
    @PermitAll
    @Operation(
        summary = "Auth health check",
        description = "Authentication servisinin sağlık durumunu kontrol eder"
    )
    fun healthCheck(): Response {
        return Response.ok(
            mapOf(
                "status" to "UP",
                "service" to "Authentication Service",
                "keycloakConfigured" to true
            )
        ).build()
    }
}

