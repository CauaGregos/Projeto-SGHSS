package com.example.vidaplus.domain.consulta;

import com.example.vidaplus.domain.prontuario.Prontuario;
import java.time.LocalDateTime;

public record ConsultaResponseDTO(
        long id,
        LocalDateTime dataHora,
        String status,
        Prontuario prontuario
) {

    public ConsultaResponseDTO(Consulta consulta) {
        this(consulta.getId(),consulta.getDataHora(), consulta.getStatus(), consulta.getProntuario());
    }
}
