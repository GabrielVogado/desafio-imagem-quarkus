package br.com.desafio.quarkus.domain.service;

import br.com.desafio.quarkus.domain.entity.Image;
import br.com.desafio.quarkus.domain.exception.ImageValidationException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ImageProcessor {

    public Image createImage(String fileName, String contentType, Long size, byte[] data) {
        validateImageData(size, data);
        // Here you can add image processing logic if needed
        return Image.builder()
                .filename(fileName)
                .contentType(contentType)
                .size(size)
                .data(data)
                .build();
    }

    private void validateImageData(Long size, byte[] data) {
        if (data == null || data.length == 0) {
            throw new ImageValidationException("Dados da imagem não podem ser vazios");
        }

        if (size == null || size <= 0) {
            throw new ImageValidationException("Tamanho da imagem deve ser maior que zero");
        }

        if (size > 10 * 1024 * 1024) { // 10MB
            throw new ImageValidationException("Imagem muito grande. Máximo 10MB permitido");
        }
    }

    public String determineContentType(String fileName, String uploadedContentType) {
        if (uploadedContentType != null && uploadedContentType.startsWith("image/")) {
            return uploadedContentType;
        }
        return getContentTypeFromExtension(fileName);
    }

    private String getContentTypeFromExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "application/octet-stream";
        }
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "bmp" -> "image/bmp";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }
}
