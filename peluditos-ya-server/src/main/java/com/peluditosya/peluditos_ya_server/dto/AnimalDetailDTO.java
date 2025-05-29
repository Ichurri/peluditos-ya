package com.peluditosya.peluditos_ya_server.dto;

// Create AnimalDetailDTO.java to format pet profile information
public class AnimalDetailDTO {
    private Long id;
    private String name;
    private String animalType;
    private String breed;
    private Integer age;
    private String behavior;
    private String medicalHistory;
    private String personalityTraits;
    private String medicalFilePath;
    private String habits;
    private String photoPath;
    private String shelterName;
    private String shelterAddress;
    private String shelterPhone;
    private Long shelterId;
    private Long sponsorId;
    
    // Getters and setters
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
    
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getMedicalFilePath() {
        return medicalFilePath;
    }

    public void setMedicalFilePath(String medicalFilePath) {
        this.medicalFilePath = medicalFilePath;
    }
    
    public String getPersonalityTraits() {
        return personalityTraits;
    }
    
    public void setPersonalityTraits(String personalityTraits) {
        this.personalityTraits = personalityTraits;
    }
    
    public String getHabits() {
        return habits;
    }
    
    public void setHabits(String habits) {
        this.habits = habits;
    }
    
    public String getPhotoPath() {
        return photoPath;
    }
    
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    
    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }
    
    public void setShelterAddress(String shelterAddress) {
        this.shelterAddress = shelterAddress;
    }
    
    public void setShelterPhone(String shelterPhone) {
        this.shelterPhone = shelterPhone;
    }

    public Long getSponsorId(){ return sponsorId; }

    public void setSponsorId(Long sponsorId1 ){ this.sponsorId = sponsorId1;}

    public Long getShelterId(){return shelterId;}

    public void setShelterId(Long shelterid){ this.shelterId = shelterid; }
}