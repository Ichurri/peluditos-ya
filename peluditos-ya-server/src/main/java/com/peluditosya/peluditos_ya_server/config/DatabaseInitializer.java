package com.peluditosya.peluditos_ya_server.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("ALTER TABLE animals DROP CONSTRAINT IF EXISTS fk7fmlpw3o4ourhtv3qy8gl6cn5");
        jdbcTemplate.execute("ALTER TABLE animals ADD CONSTRAINT fk_shelter_requests FOREIGN KEY (shelter_id) REFERENCES shelter_requests(id)");
    }
}