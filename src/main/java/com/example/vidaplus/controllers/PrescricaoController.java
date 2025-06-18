package com.example.vidaplus.controllers;

import com.example.vidaplus.domain.consulta.Consulta;
import com.example.vidaplus.domain.exception.RequisicaoInvalidaException;
import com.example.vidaplus.domain.prescricao.Prescricao;
import com.example.vidaplus.domain.prescricao.PrescricaoRequestDTO;
import com.example.vidaplus.domain.user.User;
import com.example.vidaplus.repositories.ConsultaRepository;
import com.example.vidaplus.repositories.PrescricaoRepository;
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
@RequestMapping("/prescricoes")
public class PrescricaoController {

    @Autowired
    private PrescricaoRepository repository;
    @Autowired
    private ConsultaRepository consultaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PrescricaoController.class);
    @PostMapping
    public ResponseEntity<?> emitirPrescricao(@RequestBody @Valid PrescricaoRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Consulta consulta = consultaRepository.findById(dto.consultaId())
                .orElseThrow(() -> new RequisicaoInvalidaException("Consulta não localizada."));

        Prescricao prescricao = new Prescricao(dto,consulta);
        repository.save(prescricao);
        logger.info("Usuario {} emitiu uma prescrição para a consulta com id {}",username,dto.consultaId());
        return ResponseEntity.ok().build();
    }
}
