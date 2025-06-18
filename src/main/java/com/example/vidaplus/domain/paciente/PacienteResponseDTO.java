package com.example.vidaplus.domain.paciente;

public record PacienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email
) {
    public PacienteResponseDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getTelefone(),
                paciente.getEmail()
        );
    }
}
