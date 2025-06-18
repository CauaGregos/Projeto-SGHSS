package com.example.vidaplus.domain.prontuario;

import java.time.LocalDate;

public record ProntuarioRequestDTO(
        String observacoes,
        LocalDate dataRegistro,
        Long consultaId
) {}
