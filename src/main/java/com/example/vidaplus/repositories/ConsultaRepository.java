package com.example.vidaplus.repositories;

import com.example.vidaplus.domain.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}
