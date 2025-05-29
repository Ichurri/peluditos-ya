package com.peluditosya.peluditos_ya_server.dto;

import java.time.LocalDateTime;

public class InterviewRequestDTO {

    private String userName;
    private String userEmail;
    private String petName;
    private LocalDateTime interviewDateTime;
    private String shelterEmail;

    public InterviewRequestDTO() {
    }

    public InterviewRequestDTO(String userName, String userEmail, String petName, LocalDateTime interviewDateTime, String shelterEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.petName = petName;
        this.interviewDateTime = interviewDateTime;
        this.shelterEmail = shelterEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public LocalDateTime getInterviewDateTime() {
        return interviewDateTime;
    }

    public void setInterviewDateTime(LocalDateTime interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public String getShelterEmail() {
        return shelterEmail;
    }

    public void setShelterEmail(String shelterEmail) {
        this.shelterEmail = shelterEmail;
    }
}
