package br.com.desafio.quarkus.infraestructure.controller.exception;

import br.com.desafio.quarkus.domain.exception.ImageNotFoundException;
import br.com.desafio.quarkus.domain.exception.ImageStorageException;
import br.com.desafio.quarkus.domain.exception.ImageValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {
    /**
     * Map an exception to a {@link Response}. Returning {@code null} results in a
     * {@link Response.Status#NO_CONTENT} response. Throwing a runtime exception results in a
     * {@link Response.Status#INTERNAL_SERVER_ERROR} response.
     *
     * @param exception the exception to map to a response.
     * @return a response mapped from the supplied exception.
     */
    @Override
    public Response toResponse(RuntimeException exception) {
        if (exception instanceof ImageNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        if (exception instanceof ImageStorageException ||
                exception instanceof ImageValidationException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro interno do servidor: " + exception.getMessage())
                .build();
    }
}

