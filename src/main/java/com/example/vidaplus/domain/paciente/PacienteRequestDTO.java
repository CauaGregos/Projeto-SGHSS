package com.example.vidaplus.domain.paciente;

import java.time.LocalDate;

public record PacienteRequestDTO(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String telefone,
        String email,
        String user_id
) {}
