package com.example.vidaplus.repositories;

import com.example.vidaplus.domain.profissional.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<ProfissionalSaude, Long> {
}
