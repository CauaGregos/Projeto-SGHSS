package com.example.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.vidaplus.domain.user.User;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class JwtTestUtil {
    public static String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256("minha-super-chave-secreta-top-123!");
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }
}