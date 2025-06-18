package com.example.vidaplus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videochamada")
public class VideochamadaController {
    @GetMapping("/token")
    public ResponseEntity getTokenChamada(){
        // TODO: Receberá um usuario e um profissional de saúde, vai encripitar
        // usando o id ou nome dos dois e salvar em uma tabela temporaria no banco
        return ResponseEntity.ok("Não implementado.");
    }

    @GetMapping("/iniciarChamada")
    public ResponseEntity initChamada(){
        // TODO: Vai receber o código da chamada e retornar o link da aplicação
        // de videochamada
        return ResponseEntity.ok("Não implementado.");
    }
}
