package com.example.vidaplus.controllers;

import com.example.vidaplus.domain.consulta.Consulta;
import com.example.vidaplus.domain.consulta.ConsultaRequestDTO;
import com.example.vidaplus.domain.consulta.ConsultaResponseDTO;
import com.example.vidaplus.domain.paciente.Paciente;
import com.example.vidaplus.domain.profissional.ProfissionalSaude;
import com.example.vidaplus.domain.user.User;
import com.example.vidaplus.repositories.ConsultaRepository;
import com.example.vidaplus.repositories.PacienteRepository;
import com.example.vidaplus.repositories.ProfissionalRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository repository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ProfissionalRepository profissionalRepository;

    private static final Logger logger = LoggerFactory.getLogger(ConsultaController.class);

    @PostMapping
    public ResponseEntity<?> agendarConsulta(@RequestBody @Valid ConsultaRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Paciente paciente = pacienteRepository.findById(dto.paciente_id())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        ProfissionalSaude profissional = profissionalRepository.findById(dto.profissional_id())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        Consulta consulta = new Consulta(paciente, profissional);
        repository.save(consulta);
        logger.info("Usuario {} registrou uma consulta cuja id é {}", username, consulta.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarConsulta(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        repository.deleteById(id);
        logger.info("Usuario {} deletou uma consulta cuja id é {}", username, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pacientes/{id}/consultas")
    public ResponseEntity<List<ConsultaResponseDTO>> listarConsultasPorPaciente(@PathVariable Long id) {
        Optional<Consulta> consultas = repository.findById(id);
        List<ConsultaResponseDTO> dtos = consultas.stream().map(ConsultaResponseDTO::new).toList();
        return ResponseEntity.ok(dtos);
    }
}

