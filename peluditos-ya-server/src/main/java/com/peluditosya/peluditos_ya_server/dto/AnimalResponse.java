package com.peluditosya.peluditos_ya_server.dto;

public class AnimalResponse {

    private Long id;
    private String name;
    private String animalType;
    private String breed;
    private Integer age;
    private String behavior;
    private Long shelterId;
    private String shelterName;
    private String shelterAddress;
    private String shelterPhone;
    private String medicalFilePath;
    private String photoPath;
    private String status;

    // Datos del padrino (usuario)
    private Long sponsorId;
    private String sponsorName;
    private String sponsorEmail;

    // Constructor vacío
    public AnimalResponse() {}

    // Constructor completo
    public AnimalResponse(Long id, String name, String animalType, String breed, Integer age, String behavior,
                          Long shelterId, String shelterName, String shelterAddress, String shelterPhone,
                          String medicalFilePath, String photoPath, String status,
                          Long sponsorId, String sponsorName, String sponsorEmail) {
        this.id = id;
        this.name = name;
        this.animalType = animalType;
        this.breed = breed;
        this.age = age;
        this.behavior = behavior;
        this.shelterId = shelterId;
        this.shelterName = shelterName;
        this.shelterAddress = shelterAddress;
        this.shelterPhone = shelterPhone;
        this.medicalFilePath = medicalFilePath;
        this.photoPath = photoPath;
        this.status = status;
        this.sponsorId = sponsorId;
        this.sponsorName = sponsorName;
        this.sponsorEmail = sponsorEmail;
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

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getShelterAddress() {
        return shelterAddress;
    }

    public void setShelterAddress(String shelterAddress) {
        this.shelterAddress = shelterAddress;
    }

    public String getShelterPhone() {
        return shelterPhone;
    }

    public void setShelterPhone(String shelterPhone) {
        this.shelterPhone = shelterPhone;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getSponsorEmail() {
        return sponsorEmail;
    }

    public void setSponsorEmail(String sponsorEmail) {
        this.sponsorEmail = sponsorEmail;
    }
}
