package com.example.vidaplus.repositories;

import com.example.vidaplus.domain.prescricao.Prescricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescricaoRepository extends JpaRepository<Prescricao, Long> {
}
