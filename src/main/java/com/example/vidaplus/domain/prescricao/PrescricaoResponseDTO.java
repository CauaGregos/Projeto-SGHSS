package com.example.vidaplus.domain.prescricao;

public record PrescricaoResponseDTO(
        Long id,
        String medicamento,
        String dosagem,
        String data,
        Long consultaId
) {
    public PrescricaoResponseDTO(Prescricao prescricao) {
        this(
                prescricao.getId(),
                prescricao.getMedicamento(),
                prescricao.getDosagem(),
                prescricao.getData().toString(),
                prescricao.getConsulta() != null ? prescricao.getConsulta().getId() : null
        );
    }
}
