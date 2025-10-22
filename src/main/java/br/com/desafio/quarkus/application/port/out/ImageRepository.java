package br.com.desafio.quarkus.application.port.out;

import br.com.desafio.quarkus.domain.entity.Image;
import br.com.desafio.quarkus.domain.exception.ImageNotFoundException;

public interface ImageRepository {

    Image findByFilename(String filename) throws ImageNotFoundException;

    void save(Image imageEntity);

    boolean existsByFilename(String filename);
}
