package com.peluditosya.peluditos_ya_server.model;


import jakarta.persistence.*;

@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalType animal;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Behavior behavior;

    // File paths for uploaded files
    private String medicalFilePath;
    private String photoPath;

    // Update Animal.java to include new fields for personality and habits
    private String medicalHistory;  // Detailed medical history text
    private String personalityTraits; // Pet's temperament and behavior details
    private String habits; // Pet's daily routines and preferences

    // Status field for adoption tracking
    @Column(nullable = true)
    private String status = "AVAILABLE"; // AVAILABLE, ADOPTED, IN_PROCESS, RESERVED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id", nullable = false, referencedColumnName = "id")
    private ShelterRequest ShelterRequest;
    
    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private AppUser sponsor;


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

    public AnimalType getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalType animal) {
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

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
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

    public ShelterRequest getShelterRequest() {
        return ShelterRequest;
    }

    public void setShelterRequest(ShelterRequest shelterRequest) {
        this.ShelterRequest = shelterRequest;
    }

    // Getters and setters for new fields
    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Enums
    public enum AnimalType {
        CAT, DOG
    }

    public enum Behavior {
        FRIENDLY, SHY, AGGRESSIVE, QUIET
    }

    // Getter
    public AppUser getSponsor() {
        return sponsor;
    }

    // Setter
    public void setSponsor(AppUser sponsor) {
        this.sponsor = sponsor;
    }
}