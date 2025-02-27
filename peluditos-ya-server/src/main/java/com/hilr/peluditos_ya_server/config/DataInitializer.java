package com.hilr.peluditos_ya_server.config;

import com.hilr.peluditos_ya_server.model.Mascota;
import com.hilr.peluditos_ya_server.repository.MascotaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

        @Bean
        CommandLineRunner initDatabase(MascotaRepository repository) {
                return args -> {
                        try {
                                // Only insert data if the repository is empty
                                if (repository.count() == 0) {
                                        List<Mascota> mascotas = Arrays.asList(
                                                        createMascota("Max", "Perro", "Labrador", 3,
                                                                        "Labrador amigable y juguetón. Le encanta correr y jugar con pelotas.",
                                                                        "Disponible"),
                                                        createMascota("Luna", "Perro", "Husky", 2,
                                                                        "Husky enérgica con hermosos ojos azules. Necesita mucho ejercicio diario.",
                                                                        "Disponible"),
                                                        createMascota("Michi", "Gato", "Siamés", 4,
                                                                        "Gato siamés muy cariñoso. Le gusta dormir en lugares cálidos y recibir caricias.",
                                                                        "Adoptado"),
                                                        createMascota("Rocky", "Perro", "Bulldog", 5,
                                                                        "Bulldog tranquilo y amable. Perfecto para familias con niños.",
                                                                        "Disponible"),
                                                        createMascota("Coco", "Gato", "Persa", 2,
                                                                        "Gato persa de pelo largo muy elegante. Requiere cepillado regular.",
                                                                        "En tratamiento"),
                                                        createMascota("Toby", "Perro", "Beagle", 1,
                                                                        "Beagle cachorro muy curioso y activo. Adora explorar nuevos lugares.",
                                                                        "Disponible"),
                                                        createMascota("Nina", "Gato", "Bengala", 3,
                                                                        "Gata bengala con hermoso pelaje moteado. Muy juguetona e inteligente.",
                                                                        "Disponible"),
                                                        createMascota("Lola", "Perro", "Poodle", 6,
                                                                        "Poodle adulta muy tranquila. Perfecta compañía para personas mayores.",
                                                                        "Disponible"),
                                                        createMascota("Simba", "Gato", "Común", 1,
                                                                        "Gatito naranja muy cariñoso. Rescatado de la calle y ahora busca un hogar.",
                                                                        "Disponible"),
                                                        createMascota("Rex", "Perro", "Pastor Alemán", 4,
                                                                        "Pastor alemán muy inteligente y protector. Ideal como perro guardián.",
                                                                        "En entrenamiento"));

                                        repository.saveAll(mascotas);
                                        System.out.println("Base de datos inicializada con " + repository.count()
                                                        + " mascotas");
                                }
                        } catch (Exception e) {
                                System.out.println("Error al inicializar la base de datos: " + e.getMessage());
                                System.out.println("Las tablas se crearán automáticamente en el próximo reinicio");
                        }
                };
        }

        private Mascota createMascota(String nombre, String especie, String raza,
                        Integer edad, String descripcion, String estado) {
                Mascota mascota = new Mascota();
                mascota.setNombre(nombre);
                mascota.setEspecie(especie);
                mascota.setRaza(raza);
                mascota.setEdad(edad);
                mascota.setDescripcion(descripcion);
                mascota.setEstado(estado);
                return mascota;
        }
}