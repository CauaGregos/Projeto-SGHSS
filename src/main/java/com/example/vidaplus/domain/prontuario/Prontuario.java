package com.example.vidaplus.domain.prontuario;

import com.example.vidaplus.domain.consulta.Consulta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "prontuario")
@Entity(name = "prontuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    private LocalDate dataRegistro;

    @OneToOne
    @JoinColumn(name = "consulta_id", unique = true)
    private Consulta consulta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Consulta getConsulta() {
        return consulta;
    }
    public void atualizarDados(ProntuarioRequestDTO dto) {
        this.observacoes = dto.observacoes();
        this.dataRegistro = dto.dataRegistro();
    }

    public Prontuario(ProntuarioRequestDTO dto) {
        this.observacoes = dto.observacoes();
        this.dataRegistro = dto.dataRegistro();
    }
}
