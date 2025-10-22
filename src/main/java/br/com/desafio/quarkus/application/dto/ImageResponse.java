package br.com.desafio.quarkus.application.dto;

/**
 * @param data Getters
 */
public record ImageResponse(byte[] data, String contentType, String filename) {

}
