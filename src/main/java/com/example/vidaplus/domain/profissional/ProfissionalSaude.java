package com.example.vidaplus.domain.profissional;

import com.example.vidaplus.domain.consulta.Consulta;
import com.example.vidaplus.domain.unidadeSaude.UnidadeSaude;
import com.example.vidaplus.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "profissional_saude")
@Entity(name = "profissional_saude")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProfissionalSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String crm;
    private String especialidade;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL)
    private List<Consulta> consultas = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ProfissionalSaude(ProfissionalSaudeResponseDTO dto) {
        this.nome = dto.nome();
        this.crm = dto.crm();
        this.especialidade = dto.especialidade();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
}
