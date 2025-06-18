package com.example.vidaplus.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record ConsultaRequestDTO(
        @NotNull
        Long paciente_id,
        @NotNull
        Long profissional_id
)
{ }
