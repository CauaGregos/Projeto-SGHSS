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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = VidaPlusApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ConsultaTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UnidadeSaudeRepository unidadeSaudeRepository;

    @Test
    void devePermitirAgendamentoConsultaComoPaciente() throws Exception {

        var unidadeSaude = new UnidadeSaude(new UnidadeSaudeRequestDTO("Gregos Saude"));

        unidadeSaudeRepository.save(unidadeSaude);

        User user = new User("Maria","123", UserRole.ADMIN, unidadeSaude);

        userRepository.save(user);

        String tokenAdmin = JwtTestUtil.generateToken(user);

        mockMvc.perform(post("/consultas")
                        .header("Authorization", "Bearer " + tokenAdmin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "pacienteId": 1,
                    "profissionalId": 1,
                    "dataHora": "2025-06-10T10:00:00"
                }
            """))
                .andExpect(status().isCreated());
    }

    @Test
    void deveNegarAgendamentoConsultaSemToken() throws Exception {
        mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "pacienteId": 1,
                    "profissionalId": 1,
                    "dataHora": "2025-06-10T10:00:00"
                }
            """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void devePermitirCancelarConsultaComTokenValido() throws Exception {
        var unidadeSaude = new UnidadeSaude(new UnidadeSaudeRequestDTO("Gregos Saude"));

        unidadeSaudeRepository.save(unidadeSaude);

        User user = new User("Maria","123", UserRole.ADMIN,unidadeSaude);

        userRepository.save(user);

        String tokenAdmin = JwtTestUtil.generateToken(user);

        mockMvc.perform(delete("/consultas/1")
                        .header("Authorization", "Bearer " + tokenAdmin))
                .andExpect(status().isNoContent());
    }
}
