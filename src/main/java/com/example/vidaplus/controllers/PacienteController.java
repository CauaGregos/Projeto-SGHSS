package com.example.vidaplus.controllers;

import com.example.vidaplus.domain.paciente.Paciente;
import com.example.vidaplus.domain.paciente.PacienteRequestDTO;
import com.example.vidaplus.domain.user.User;
import com.example.vidaplus.repositories.PacienteRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    @PostMapping
    public ResponseEntity<?> criarPaciente(@RequestBody @Valid PacienteRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Paciente paciente = new Paciente(dto);
        repository.save(paciente);

        logger.info("Usuario {} criou o paciente com id {}",username,paciente.getId());
        return ResponseEntity.ok().build();
    }
}
