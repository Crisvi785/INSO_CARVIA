package com.carvia.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.util.Map;

public class CloudinaryController {

    private final Cloudinary cloudinary;

    public CloudinaryController() {
        Dotenv dotenv = Dotenv.load();
        String cloudinaryUrl = dotenv.get("CLOUDINARY_URL");
        if (cloudinaryUrl == null || cloudinaryUrl.isEmpty()) {
            throw new IllegalArgumentException("CLOUDINARY_URL no está configurada en el archivo .env");
        }
        this.cloudinary = new Cloudinary(cloudinaryUrl);
    }

    // Método para subir una imagen a Cloudinary
    public String uploadImage(File image) {
        try {
            // Configuración de los parámetros para la subida
            Map<String, Object> params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
            );

            // Subir la imagen
            Map uploadResult = cloudinary.uploader().upload(image, params);

            // Obtener la URL pública de la imagen subida
            String imageUrl = (String) uploadResult.get("url");
            System.out.println("Imagen subida con éxito: " + imageUrl);

            return imageUrl; // Retorna la URL de la imagen subida
        } catch (Exception e) {
            System.err.println("Error subiendo la imagen " + image.getName() + ": " + e.getMessage());
            return null;
        }
    }
}
