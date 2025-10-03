package com.example.core.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserEventDTO {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String operation;

    public UserEventDTO() {
    }

    public UserEventDTO(String email, String operation) {
        this.email = email;
        this.operation = operation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}