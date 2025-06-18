package com.example.vidaplus.domain.profissional;

public record ProfissionalSaudeResponseDTO(
        Long id,
        String nome,
        String crm,
        String especialidade,
        String user_id
) {
    public ProfissionalSaudeResponseDTO(ProfissionalSaude profissional) {
        this(
                profissional.getId(),
                profissional.getNome(),
                profissional.getCrm(),
                profissional.getEspecialidade(),
                profissional.getUser().getId()
        );
    }
}
