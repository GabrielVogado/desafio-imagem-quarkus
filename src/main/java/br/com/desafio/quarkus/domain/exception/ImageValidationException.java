package br.com.desafio.quarkus.domain.exception;

public class ImageValidationException extends RuntimeException {
    public ImageValidationException(String message) {
        super(message);
    }
}
