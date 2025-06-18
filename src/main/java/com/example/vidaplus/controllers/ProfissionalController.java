package com.example.vidaplus.controllers;

import com.example.vidaplus.domain.exception.RequisicaoInvalidaException;
import com.example.vidaplus.domain.profissional.ProfissionalSaude;
import com.example.vidaplus.domain.profissional.ProfissionalSaudeResponseDTO;
import com.example.vidaplus.domain.user.User;
import com.example.vidaplus.repositories.ProfissionalRepository;
import com.example.vidaplus.repositories.UserRepository;
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
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository repository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProfissionalController.class);

    @PostMapping
    public ResponseEntity<?> cadastrarProfissional(@RequestBody @Valid ProfissionalSaudeResponseDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        var user = userRepository.findById(dto.user_id())
                .orElseThrow(() -> new RequisicaoInvalidaException("Usuario não encontrado, tente novamente"));

        ProfissionalSaude profissional = new ProfissionalSaude(dto);
        repository.save(profissional);
        logger.info("Usuario {} criou o profissional cujo id é {}",username,profissional.getId());
        return ResponseEntity.ok().build();
    }
}
