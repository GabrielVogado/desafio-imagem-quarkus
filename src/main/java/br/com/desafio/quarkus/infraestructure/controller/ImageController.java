package br.com.desafio.quarkus.infraestructure.controller;

import br.com.desafio.quarkus.application.dto.ImageResponse;
import br.com.desafio.quarkus.application.port.in.ImageService;
import br.com.desafio.quarkus.domain.exception.ImageNotFoundException;
import br.com.desafio.quarkus.domain.exception.ImageStorageException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/images")
public class ImageController {

    @Inject
    ImageService imageService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@RestForm("file") FileUpload file) {
        try {
            imageService.savedUploadImage(file);
            return Response.ok("Upload de Imagem feito com Sucesso").build();
        } catch (ImageStorageException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro no upload da imagem" + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/download/{imageName}")
    public Response downloadImage(@PathParam("imageName") String imageName) {
        try {
            ImageResponse imageResponse = imageService.getDownloadImage(imageName);

            return Response.ok(imageResponse.data())
                    .type(imageResponse.contentType())
                    .header("Content-Disposition",
                            "inline; filename=\"" + imageResponse.filename() + "\"")
                    .build();

        } catch (ImageNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (ImageStorageException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao recuperar imagem: " + e.getMessage())
                    .build();
        }
    }
}
