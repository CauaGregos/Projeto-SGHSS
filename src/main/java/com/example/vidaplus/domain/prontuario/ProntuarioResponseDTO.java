package com.example.vidaplus.domain.prontuario;

public record ProntuarioResponseDTO(
        Long id,
        String observacoes,
        String dataRegistro,
        Long consultaId
) {
    public ProntuarioResponseDTO(Prontuario prontuario) {
        this(
                prontuario.getId(),
                prontuario.getObservacoes(),
                prontuario.getDataRegistro() != null ? prontuario.getDataRegistro().toString() : null,
                prontuario.getConsulta() != null ? prontuario.getConsulta().getId() : null
        );
    }
}
