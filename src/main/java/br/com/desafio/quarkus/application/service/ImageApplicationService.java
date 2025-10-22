package br.com.desafio.quarkus.application.service;

import br.com.desafio.quarkus.application.dto.ImageResponse;
import br.com.desafio.quarkus.application.port.out.ImageRepository;
import br.com.desafio.quarkus.domain.entity.Image;
import br.com.desafio.quarkus.domain.exception.ImageNotFoundException;
import br.com.desafio.quarkus.domain.exception.ImageStorageException;
import br.com.desafio.quarkus.domain.service.ImageProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.nio.file.Files;

@ApplicationScoped
public class ImageApplicationService {

    @Inject
    ImageRepository imageRepository;

    @Inject
    ImageProcessor imageProcessor;


    public void uploadImageToDatabase(FileUpload file) throws ImageStorageException {

        try {
            byte[] bytes = Files.readAllBytes(file.uploadedFile().toRealPath());
            Image image = imageProcessor.createImage(file.fileName(), file.contentType(), file.size(), bytes);
            imageRepository.save(image);
        } catch (Exception e) {
            throw new ImageStorageException("Erro ao salvar imagem", e);
        }
    }

    public ImageResponse downloadImageFromDatabase(String nameImage) throws ImageNotFoundException, ImageStorageException {
        try {
            Image image = imageRepository.findByFilename(nameImage);
            return new ImageResponse(image.getData(), image.getContentType(), image.getFilename());
        } catch (ImageNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ImageStorageException("Erro ao baixar imagem" + e.getMessage(), e);
        }
    }
}
