package br.com.desafio.quarkus.domain.exception;

public class ImageStorageException extends RuntimeException {

    public ImageStorageException(String mensage) {
        super(mensage);
    }

    public ImageStorageException(String mensage, Throwable cause) {
        super(mensage, cause);
    }
}
