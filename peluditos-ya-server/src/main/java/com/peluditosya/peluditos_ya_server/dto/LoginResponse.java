package com.peluditosya.peluditos_ya_server.dto;

public class LoginResponse {
    private String message;
    private boolean admin;

    public LoginResponse(String message, boolean admin) {
        this.message = message;
        this.admin = admin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
