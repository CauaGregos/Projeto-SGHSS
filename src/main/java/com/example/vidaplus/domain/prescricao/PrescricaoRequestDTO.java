package com.example.vidaplus.domain.prescricao;

import java.time.LocalDate;

public record PrescricaoRequestDTO(
        String medicamento,
        String dosagem,
        LocalDate data,
        Long consultaId
) {}