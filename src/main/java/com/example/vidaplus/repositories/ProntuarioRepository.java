package com.example.vidaplus.repositories;

import com.example.vidaplus.domain.prontuario.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
}
