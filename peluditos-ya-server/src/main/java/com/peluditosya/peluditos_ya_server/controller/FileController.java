package com.peluditosya.peluditos_ya_server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final Path baseUploadDir;
    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(@Value("${file.storage.upload-dir}") String uploadDir) {
        this.baseUploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @GetMapping("/animal-photos/{filename:.+}")
    public ResponseEntity<Resource> getAnimalPhoto(@PathVariable String filename) {
        return getFile("animal-photos", filename);
    }

    @GetMapping("/medical-files/{filename:.+}")
    public ResponseEntity<Resource> getMedicalFile(@PathVariable String filename) {
        return getFile("medical-files", filename);
    }

    private ResponseEntity<Resource> getFile(String subfolder, String filename) {
        try {
            Path filePath = baseUploadDir.resolve(subfolder).resolve(filename).normalize();
            
            // Security check - ensure the file is within the upload directory
            if (!filePath.startsWith(baseUploadDir)) {
                logger.warn("Attempt to access file outside upload directory: {}", filePath);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists() || !resource.isReadable()) {
                logger.warn("File not found or not readable: {}", filePath);
                return ResponseEntity.notFound().build();
            }

            // Determine content type
            String contentType = determineContentType(filePath);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
                    
        } catch (MalformedURLException e) {
            logger.error("Error creating resource for file: {}/{}", subfolder, filename, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String determineContentType(Path filePath) {
        try {
            String contentType = Files.probeContentType(filePath);
            if (contentType != null) {
                return contentType;
            }
        } catch (IOException e) {
            logger.debug("Could not determine content type for file: {}", filePath);
        }
        
        // Fallback based on file extension
        String filename = filePath.getFileName().toString().toLowerCase();
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filename.endsWith(".png")) {
            return "image/png";
        } else if (filename.endsWith(".gif")) {
            return "image/gif";
        } else if (filename.endsWith(".pdf")) {
            return "application/pdf";
        }
        
        return "application/octet-stream";
    }
}
