package com.example.vidaplus.controllers;

import com.example.vidaplus.domain.product.ProductResponseDTO;
import com.example.vidaplus.domain.unidadeSaude.UnidadeSaude;
import com.example.vidaplus.domain.unidadeSaude.UnidadeSaudeRequestDTO;
import com.example.vidaplus.domain.unidadeSaude.UnidadeSaudeResponseDTO;
import com.example.vidaplus.domain.user.User;
import com.example.vidaplus.repositories.UnidadeSaudeRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidade-saude")
public class UnidadeSaudeController {

    @Autowired
    private UnidadeSaudeRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(UnidadeSaudeController.class);

    @PostMapping("/{id}")
    public ResponseEntity<?> cadastrarUnidadeSaude(@RequestBody @Valid UnidadeSaudeRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        UnidadeSaude unidadeSaude = new UnidadeSaude(dto);
        repository.save(unidadeSaude);
        logger.info("Usuario {} criou a unidade de saude {}",username, unidadeSaude.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirUnidadeSaude(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        repository.deleteById(id);
        logger.info("Usuario {} deletou a unidade de saude {}", username, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity buscarTodos(){
        List<UnidadeSaudeResponseDTO> unidadeSaudeList = this.repository.findAll().stream().map(UnidadeSaudeResponseDTO::new).toList();

        return ResponseEntity.ok(unidadeSaudeList);
    }

}
