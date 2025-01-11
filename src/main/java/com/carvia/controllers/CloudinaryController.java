package com.carvia.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.util.Map;

public class CloudinaryController {

    private final Cloudinary cloudinary;

    // Constructor: Inicializa Cloudinary con la URL desde variables de entorno
    public Cloudinary getCloudinary() {
        return cloudinary;
    }
    
    public CloudinaryController() {
        Dotenv dotenv = Dotenv.load();
        String cloudinaryUrl = dotenv.get("CLOUDINARY_URL");
        if (cloudinaryUrl == null || cloudinaryUrl.isEmpty()) {
            throw new IllegalArgumentException("CLOUDINARY_URL no está configurada en el archivo .env");
        }
        cloudinary = new Cloudinary(cloudinaryUrl);
    }

    // Método para subir una imagen a Cloudinary
    public Map uploadImage(String imagePath) {
        try {
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
            );

            System.out.println("Subiendo imagen...");
            return cloudinary.uploader().upload(imagePath, uploadParams);
        } catch (Exception e) {
            System.err.println("Error subiendo la imagen: " + e.getMessage());
            return null;
        }
    }

    // Método para obtener detalles de un recurso en Cloudinary
    public Map getResourceDetails(String publicId) {
        try {
            Map<String, Object> resourceParams = ObjectUtils.asMap(
                "quality_analysis", true
            );

            System.out.println("Obteniendo detalles del recurso...");
            return cloudinary.api().resource(publicId, resourceParams);
        } catch (Exception e) {
            System.err.println("Error obteniendo detalles del recurso: " + e.getMessage());
            return null;
        }
    }

    // Método para generar una etiqueta HTML con transformación
    public String generateImageTag(String publicId) {
        try {
            System.out.println("Generando etiqueta HTML...");
            return cloudinary.url()
                .transformation(new Transformation()
                    .crop("pad")
                    .width(300)
                    .height(400)
                    .background("auto:predominant"))
                .imageTag(publicId);
        } catch (Exception e) {
            System.err.println("Error generando la etiqueta HTML: " + e.getMessage());
            return null;
        }
    }

   
}
