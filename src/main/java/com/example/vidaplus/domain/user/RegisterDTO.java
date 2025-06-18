package com.example.vidaplus.domain.user;

public record RegisterDTO(String login, String password, UserRole role, Long unidade_saude_id) {
}
