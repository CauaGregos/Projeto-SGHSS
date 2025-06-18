package com.example.vidaplus.controllers;

import com.example.vidaplus.domain.exception.RequisicaoInvalidaException;
import com.example.vidaplus.domain.user.AuthenticationDTO;
import com.example.vidaplus.domain.user.LoginResponseDTO;
import com.example.vidaplus.domain.user.RegisterDTO;
import com.example.vidaplus.domain.user.User;
import com.example.vidaplus.infra.security.TokenService;
import com.example.vidaplus.repositories.UnidadeSaudeRepository;
import com.example.vidaplus.repositories.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UnidadeSaudeRepository unidadeSaudeRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            logger.info("Usuario {} logado no sistema",data.login());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            throw new RequisicaoInvalidaException("Login ou senha incorretos");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        var unidadeSaude = this.unidadeSaudeRepository.findById(data.unidade_saude_id());
        if(unidadeSaude.isEmpty()) throw new RequisicaoInvalidaException("Não existe uma unidade de saúde com este ID");
        if(this.repository.findByLogin(data.login()) != null) throw new RequisicaoInvalidaException("Não é possivel usar este email");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role(),unidadeSaude.get());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
