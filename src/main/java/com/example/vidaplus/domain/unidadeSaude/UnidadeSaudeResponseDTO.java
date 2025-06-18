package com.example.vidaplus.domain.unidadeSaude;

public record UnidadeSaudeResponseDTO(Long id, String nome) {
    public UnidadeSaudeResponseDTO(UnidadeSaude unidadeSaude){
        this(unidadeSaude.getId(),unidadeSaude.getNome());
    }
}
