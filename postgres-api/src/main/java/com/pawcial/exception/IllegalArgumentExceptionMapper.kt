package com.pawcial.exception

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class IllegalArgumentExceptionMapper : ExceptionMapper<IllegalArgumentException> {

    override fun toResponse(exception: IllegalArgumentException): Response {
        return Response.status(Response.Status.CONFLICT)
            .entity(mapOf(
                "error" to "Conflict",
                "message" to exception.message,
                "status" to 409
            ))
            .build()
    }
}

