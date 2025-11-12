package com.pawcial.exception

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.jboss.logging.Logger

/**
 * Unauthorized (401) exception handler
 *
 * Token geçersiz veya eksik olduğunda tetiklenir
 */
@Provider
class UnauthorizedExceptionMapper : ExceptionMapper<io.quarkus.security.UnauthorizedException> {

    private val logger = Logger.getLogger(UnauthorizedExceptionMapper::class.java)

    override fun toResponse(exception: io.quarkus.security.UnauthorizedException): Response {
        logger.warn("Unauthorized access attempt: ${exception.message}")

        return Response
            .status(Response.Status.UNAUTHORIZED)
            .entity(
                mapOf(
                    "error" to "Unauthorized",
                    "message" to "Authentication is required. Please provide a valid Bearer token.",
                    "timestamp" to System.currentTimeMillis()
                )
            )
            .build()
    }
}

/**
 * Forbidden (403) exception handler
 *
 * Kullanıcı kimlik doğrulaması yapmış ama yetkisi yok
 */
@Provider
class ForbiddenExceptionMapper : ExceptionMapper<io.quarkus.security.ForbiddenException> {

    private val logger = Logger.getLogger(ForbiddenExceptionMapper::class.java)

    override fun toResponse(exception: io.quarkus.security.ForbiddenException): Response {
        logger.warn("Forbidden access attempt: ${exception.message}")

        return Response
            .status(Response.Status.FORBIDDEN)
            .entity(
                mapOf(
                    "error" to "Forbidden",
                    "message" to "You don't have permission to access this resource.",
                    "timestamp" to System.currentTimeMillis()
                )
            )
            .build()
    }
}

/**
 * Authentication Failed exception handler
 *
 * Token doğrulama hatası olduğunda tetiklenir
 */
@Provider
class AuthenticationFailedExceptionMapper : ExceptionMapper<io.quarkus.security.AuthenticationFailedException> {

    private val logger = Logger.getLogger(AuthenticationFailedExceptionMapper::class.java)

    override fun toResponse(exception: io.quarkus.security.AuthenticationFailedException): Response {
        logger.error("Authentication failed: ${exception.message}", exception)

        return Response
            .status(Response.Status.UNAUTHORIZED)
            .entity(
                mapOf(
                    "error" to "Authentication Failed",
                    "message" to "Invalid or expired token. Please login again.",
                    "timestamp" to System.currentTimeMillis()
                )
            )
            .build()
    }
}

