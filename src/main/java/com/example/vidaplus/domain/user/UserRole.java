package com.example.vidaplus.domain.user;

public enum UserRole {
    ADMIN("admin"),
    MEDICO("medico"),
    PROFISSIONALSAUDE("profissional de saude"),
    PACIENTE("paciente");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
