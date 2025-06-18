package com.example.vidaplus.domain.unidadeSaude;

import com.example.vidaplus.domain.paciente.Paciente;
import com.example.vidaplus.domain.prescricao.Prescricao;
import com.example.vidaplus.domain.profissional.ProfissionalSaude;
import com.example.vidaplus.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "unidade_saude")
@Entity(name = "unidade_saude")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UnidadeSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String nome;

    @OneToMany(mappedBy = "unidadeSaude", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public UnidadeSaude(UnidadeSaudeRequestDTO dto){
        this.nome = dto.nome();
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }
}
