package com.peluditosya.peluditos_ya_server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Path baseUploadDir;
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    public FileStorageService(@Value("${file.storage.upload-dir}") String uploadDir) {
        this.baseUploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public String storeFile(MultipartFile file, String subfolder) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            Path targetDir = baseUploadDir.resolve(subfolder);
            Files.createDirectories(targetDir);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path targetPath = targetDir.resolve(fileName);
            file.transferTo(targetPath.toFile());
            return targetPath.toString();
        } catch (IOException e) {
            logger.error("Error storing file {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Could not store file: " + file.getOriginalFilename(), e);
        }
    }
}