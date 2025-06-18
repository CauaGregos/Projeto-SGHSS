package com.example.vidaplus.controllers;

import com.example.vidaplus.domain.consulta.Consulta;
import com.example.vidaplus.domain.exception.RequisicaoInvalidaException;
import com.example.vidaplus.domain.prontuario.Prontuario;
import com.example.vidaplus.domain.prontuario.ProntuarioRequestDTO;
import com.example.vidaplus.domain.user.User;
import com.example.vidaplus.repositories.ConsultaRepository;
import com.example.vidaplus.repositories.ProntuarioRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

    @Autowired
    private ProntuarioRepository repository;
    @Autowired
    private ConsultaRepository consultaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProntuarioController.class);

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProntuario(@PathVariable Long id, @RequestBody @Valid ProntuarioRequestDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Prontuario prontuario = repository.findById(id)
                .orElseThrow(() -> new RequisicaoInvalidaException("Prontuario não encontrado"));

        var observacaoAntiga = prontuario.getObservacoes();
        prontuario.atualizarDados(dto);
        repository.save(prontuario);
        logger.info("Usuario {} atualizou o prontuario id {} observações antigas: {}",username,id,observacaoAntiga);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> cadastrarProntuario(@RequestBody @Valid ProntuarioRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        var consulta = consultaRepository.findById(dto.consultaId())
                .orElseThrow(() -> new RequisicaoInvalidaException("Consulta não encontrada"));

        Prontuario prontuario = new Prontuario(dto,consulta);
        repository.save(prontuario);
        logger.info("Usuario {} criou o prontuario {} para a consulta {}", username, prontuario.getId(), dto.consultaId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirProntuario(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Prontuario prontuario = repository.findById(id)
                .orElseThrow(() -> new RequisicaoInvalidaException("Prontuario não encontrado"));

        repository.deleteById(id);
        logger.info("Usuario {} deletou o prontuario {}", username, id);
        return ResponseEntity.noContent().build();
    }
}
