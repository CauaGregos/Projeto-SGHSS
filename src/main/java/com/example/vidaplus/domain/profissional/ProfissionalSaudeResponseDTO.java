package com.example.vidaplus.domain.profissional;

public record ProfissionalSaudeResponseDTO(
        Long id,
        String nome,
        String crm,
        String especialidade
) {
    public ProfissionalSaudeResponseDTO(ProfissionalSaude profissional) {
        this(
                profissional.getId(),
                profissional.getNome(),
                profissional.getCrm(),
                profissional.getEspecialidade()
        );
    }
}
