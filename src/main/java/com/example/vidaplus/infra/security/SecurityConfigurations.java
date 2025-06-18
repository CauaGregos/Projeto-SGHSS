package com.example.vidaplus.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain SecurityFilterChain  (HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
                        .requestMatchers("/pacientes/**").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET,"/pacientes/**").hasAnyRole("PACIENTE","PROFISSIONALSAUDE","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/consultas/**").hasAnyRole("PACIENTE","PROFISSIONALSAUDE","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/consultas/**").hasAnyRole("PACIENTE","PROFISSIONALSAUDE","ADMIN")
                        .requestMatchers("/prontuarios/**").hasAnyRole("MEDICO","ADMIN")
                        .requestMatchers("/prescricoes/**").hasAnyRole("MEDICO","ADMIN")
                        .requestMatchers("/profissionais/**").hasRole("ADMIN")
                        .requestMatchers("/unidadeSaude/**").hasRole("ADMIN")
                        .requestMatchers("/videochamada/**").hasAnyRole("PROFISSIONALSAUDE","ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
