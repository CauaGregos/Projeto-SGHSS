package com.example.vidaplus.domain.consulta;

import com.example.vidaplus.domain.paciente.Paciente;
import com.example.vidaplus.domain.profissional.ProfissionalSaude;
import com.example.vidaplus.domain.prontuario.Prontuario;
import com.example.vidaplus.domain.prescricao.Prescricao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "consulta")
@Entity(name = "consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;
    private String status;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalSaude profissional;

    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL)
    private Prontuario prontuario;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL)
    private List<Prescricao> prescricoes = new ArrayList<>();


    public Consulta(Paciente paciente, ProfissionalSaude profissional) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.dataHora = LocalDateTime.now();
        this.status = "Aberta";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Prescricao> getPrescricoes() {
        return prescricoes;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public ProfissionalSaude getProfissional() {
        return profissional;
    }

    public Paciente getPaciente() {
        return paciente;
    }
}
