package com.peluditosya.peluditos_ya_server.dto;

public class AnimalResponse {

    private Long id;
    private String name;
    private String animalType;  // Tipo de animal
    private String breed;
    private Integer age;
    private String behavior;
    private Long shelterId;  // ID del refugio (relación con Shelter)
    private String medicalFilePath;
    private String photoPath;

    // Constructor sin parámetros
    public AnimalResponse() {}

    // Constructor con parámetros
    public AnimalResponse(Long id, String name, String animalType, String breed, Integer age, String behavior, Long shelterId, String medicalFilePath, String photoPath) {
        this.id = id;
        this.name = name;
        this.animalType = animalType;
        this.breed = breed;
        this.age = age;
        this.behavior = behavior;
        this.shelterId = shelterId;
        this.medicalFilePath = medicalFilePath;
        this.photoPath = photoPath;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

    public String getMedicalFilePath() {
        return medicalFilePath;
    }

    public void setMedicalFilePath(String medicalFilePath) {
        this.medicalFilePath = medicalFilePath;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
