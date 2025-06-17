package com.peluditosya.peluditos_ya_server.config;

import com.peluditosya.peluditos_ya_server.model.Adopter;
import com.peluditosya.peluditos_ya_server.model.Role;
import com.peluditosya.peluditos_ya_server.model.Shelter;
import com.peluditosya.peluditos_ya_server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AdminUserInitializer.class);

    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        createAdminUserIfNotExists();
        createTestShelterUserIfNotExists();
    }

    private void createAdminUserIfNotExists() {
        String adminEmail = "admin@peluditosya.com";
        
        if (userRepository.findByEmail(adminEmail) == null) {
            // Crear usuario administrador usando la clase Adopter como base
            Adopter admin = new Adopter();
            admin.setName("Administrador Sistema");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setCity("Sistema");
            admin.setPhone("0000000000");
            
            // Cambiar el rol a ADMIN después de la creación
            admin.setRole(Role.ADMIN);
            
            userRepository.save(admin);
            
            logger.info("✅ Usuario administrador creado exitosamente:");
            logger.info("📧 Email: {}", adminEmail);
            logger.info("🔑 Password: admin123");
            logger.info("⚠️  IMPORTANTE: Cambia la contraseña en producción!");
        } else {
            logger.info("ℹ️  Usuario administrador ya existe");
        }
    }

    private void createTestShelterUserIfNotExists() {
        String shelterEmail = "refugio@test.com";
        
        if (userRepository.findByEmail(shelterEmail) == null) {
            // Crear usuario refugio de prueba
            Shelter shelter = new Shelter();
            shelter.setName("Encargado Refugio Test");
            shelter.setEmail(shelterEmail);
            shelter.setPassword(passwordEncoder.encode("refugio123"));
            shelter.setCity("Ciudad Test");
            shelter.setPhone("1234567890");
            shelter.setShelterName("Refugio de Prueba");
            shelter.setShelterAddress("Dirección Test 123");
            // El constructor de Shelter ya asigna el rol SHELTER
            
            userRepository.save(shelter);
            
            logger.info("✅ Usuario refugio de prueba creado exitosamente:");
            logger.info("📧 Email: {}", shelterEmail);
            logger.info("🔑 Password: refugio123");
            logger.info("🏠 Refugio: Refugio de Prueba");
        } else {
            logger.info("ℹ️  Usuario refugio de prueba ya existe");
        }
    }
}
