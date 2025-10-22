package br.com.desafio.quarkus.infraestructure.service.adapter;

import br.com.desafio.quarkus.application.dto.ImageResponse;
import br.com.desafio.quarkus.application.port.in.ImageService;
import br.com.desafio.quarkus.application.service.ImageApplicationService;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@ApplicationScoped
@IfBuildProfile("db")
public class DBAImagedapter implements ImageService {

    @Inject
    ImageApplicationService imageApplicationService;

    @Override
    @Transactional
    public void savedUploadImage(FileUpload file) {
        imageApplicationService.uploadImageToDatabase(file);
    }

    @Override
    public ImageResponse getDownloadImage(String nameImage) {
        return imageApplicationService.downloadImageFromDatabase(nameImage);
    }
}
