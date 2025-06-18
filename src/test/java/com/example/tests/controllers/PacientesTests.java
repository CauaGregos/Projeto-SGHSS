package com.example.tests.controllers;

import com.example.vidaplus.VidaPlusApplication;
import com.example.vidaplus.domain.unidadeSaude.UnidadeSaude;
import com.example.vidaplus.domain.unidadeSaude.UnidadeSaudeRequestDTO;
import com.example.vidaplus.domain.user.User;
import com.example.vidaplus.domain.user.UserRole;
import com.example.vidaplus.repositories.UnidadeSaudeRepository;
import com.example.vidaplus.repositories.UserRepository;
import com.example.util.JwtTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = VidaPlusApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PacientesTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UnidadeSaudeRepository unidadeSaudeRepository;

    @Test
    void devePermitirCadastroPacienteComTokenAdmin() throws Exception {

        var unidadeSaude = new UnidadeSaude(new UnidadeSaudeRequestDTO("Gregos Saude"));

        unidadeSaudeRepository.save(unidadeSaude);

        User user = new User("Maria","123", UserRole.ADMIN,unidadeSaude);

        userRepository.save(user);

        String tokenAdmin = JwtTestUtil.generateToken(user);
        mockMvc.perform(post("/pacientes")
                        .with(csrf())
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                   "nome": "string",
                   "cpf": "string",
                   "dataNascimento": "2025-06-09",
                   "telefone": "string",
                   "email": "string"
                 }
            """))
                .andExpect(status().isOk());
    }

    @Test
    void deveNegarCadastroPacienteSemPermissao() throws Exception {
        var unidadeSaude = new UnidadeSaude(new UnidadeSaudeRequestDTO("Gregos Saude"));

        unidadeSaudeRepository.save(unidadeSaude);

        User user = new User("Maria","123", UserRole.PROFISSIONALSAUDE,unidadeSaude);

        userRepository.save(user);

        String tokenAdmin = JwtTestUtil.generateToken(user);

        mockMvc.perform(post("/pacientes")
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                   "nome": "string",
                   "cpf": "string",
                   "dataNascimento": "2025-06-09",
                   "telefone": "string",
                   "email": "string"
                 }
            """))
                .andExpect(status().isForbidden());
    }

    @Test
    void devePermitirListarConsultasDoPaciente() throws Exception {
        var unidadeSaude = new UnidadeSaude(new UnidadeSaudeRequestDTO("Gregos Saude"));

        unidadeSaudeRepository.save(unidadeSaude);

        User user = new User("Maria","123", UserRole.ADMIN,unidadeSaude);

        userRepository.save(user);

        String tokenAdmin = JwtTestUtil.generateToken(user);

        mockMvc.perform(get("/pacientes/1/consultas")
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(status().isOk());
    }


}
