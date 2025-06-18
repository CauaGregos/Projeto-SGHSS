package com.example.vidaplus.domain.prescricao;

import com.example.vidaplus.domain.consulta.Consulta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "prescricao")
@Entity(name = "prescricao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Prescricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicamento;
    private String dosagem;
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    public Prescricao(PrescricaoRequestDTO dto, Consulta consulta) {
        this.medicamento = dto.medicamento();
        this.dosagem = dto.dosagem();
        this.data = dto.data();
        this.consulta = consulta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
