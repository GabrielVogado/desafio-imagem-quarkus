package br.com.desafio.quarkus.infraestructure.repositories;

import br.com.desafio.quarkus.application.port.out.ImageRepository;
import br.com.desafio.quarkus.domain.entity.Image;
import br.com.desafio.quarkus.domain.exception.ImageNotFoundException;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@IfBuildProfile("db")
public class ImageJpaRepository implements ImageRepository, PanacheRepository<ImageEntity> {

    @Override
    public Image findByFilename(String filename) throws ImageNotFoundException {
        ImageEntity entity = find("filename", filename).firstResult();
        if (entity == null) {
            throw new ImageNotFoundException("Image not found with filename: " + filename);
        }
        return toDomain(entity);
    }

    @Override
    @Transactional
    public void save(Image image) {
        ImageEntity entity = toEntity(image);
        persist(entity);
        image.setId(entity.id);

    }

    @Override
    public boolean existsByFilename(String filename) {
        return count("filename", filename) > 0;
    }

    private Image toDomain(ImageEntity entity) {
        return Image.builder()
                .id(entity.id)
                .filename(entity.getFilename())
                .contentType(entity.getContentType())
                .size(entity.getSize())
                .data(entity.getData())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private ImageEntity toEntity(Image imageEntity) {
        return ImageEntity.builder()
                .filename(imageEntity.getFilename())
                .contentType(imageEntity.getContentType())
                .size(imageEntity.getSize())
                .data(imageEntity.getData())
                .createdAt(imageEntity.getCreatedAt())
                .build();
    }
}
