package br.com.desafio.quarkus.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private Long id;
    private String filename;
    private String contentType;
    private Long size;
    private byte[] data;
    private Instant createdAt;

    // Métodos de domínio (se houver regras de negócio)
    public boolean isValid() {
        return data != null && data.length > 0 && size > 0;
    }
}
