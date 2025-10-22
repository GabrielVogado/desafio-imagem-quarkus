package br.com.desafio.quarkus.infraestructure.service.adapter;

import br.com.desafio.quarkus.application.dto.ImageResponse;
import br.com.desafio.quarkus.application.port.in.ImageService;
import br.com.desafio.quarkus.domain.exception.ImageNotFoundException;
import br.com.desafio.quarkus.domain.exception.ImageStorageException;
import br.com.desafio.quarkus.domain.service.ImageProcessor;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@ApplicationScoped
@IfBuildProfile("local")
public class LocalImageAdapter implements ImageService {

    private static final String LOCAL_PATH = "C:/Users/Gabriel Vogado/Downloads/";

    @Inject
    ImageProcessor imageProcessor;

    @Override
    @Transactional
    public void savedUploadImage(FileUpload file) {
        try {
            Path uploadPath = Paths.get(LOCAL_PATH);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(
                    file.uploadedFile(),
                    uploadPath.resolve(file.fileName()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new ImageStorageException("Falha ao carregar imagem: " + e.getMessage(), e);
        }
    }

    @Override
    public ImageResponse getDownloadImage(String nameImage) {
        try {
            Path imagePath = Paths.get(LOCAL_PATH).resolve(nameImage);

            if (!Files.exists(imagePath)) {
                throw new ImageNotFoundException("Imagem não encontrada: " + nameImage);
            }

            byte[] imageBytes = Files.readAllBytes(imagePath);
            String contentType = imageProcessor.determineContentType(nameImage, null);

            return new ImageResponse(imageBytes, contentType, nameImage);

        } catch (IOException e) {
            throw new ImageStorageException("Falha ao baixar arquivo de imagem: " + e.getMessage(), e);
        }
    }
}