package br.com.desafio.quarkus.application.port.in;

import br.com.desafio.quarkus.application.dto.ImageResponse;
import br.com.desafio.quarkus.domain.exception.ImageNotFoundException;
import br.com.desafio.quarkus.domain.exception.ImageStorageException;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public interface ImageService {

    void savedUploadImage(FileUpload file) throws ImageStorageException;

    ImageResponse getDownloadImage(String nameImage) throws ImageNotFoundException, ImageStorageException;
}
