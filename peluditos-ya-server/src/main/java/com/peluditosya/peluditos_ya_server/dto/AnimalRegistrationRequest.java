package com.peluditosya.peluditos_ya_server.dto;

import org.springframework.web.multipart.MultipartFile;

public class AnimalRegistrationRequest {
    private String name;
    private String animal; // CAT or DOG
    private String breed;
    private Integer age;
    private String behavior; // FRIENDLY, SHY, AGGRESSIVE, QUIET
    private MultipartFile medicalFile;
    private MultipartFile photo;
    private Long shelterId;
    private String photoUrl;


    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
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

    public MultipartFile getMedicalFile() {
        return medicalFile;
    }

    public void setMedicalFile(MultipartFile medicalFile) {
        this.medicalFile = medicalFile;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public Long getShelterId() {
        return shelterId;
    }

    public void setShelterId(Long shelterId) {
        this.shelterId = shelterId;
    }

    public String getPhotoUrl() {
    return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}