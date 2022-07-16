package com.SoulCode.servicos.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils { // gerenciar e gerar tokens
    @Value("${jwt.secret}") // senha vinda do applicationProperties
    private String secret;

    @Value("${jwt.expiration}") // tempo vindo do applicationProperties
    private Long expiration;

    public String generateToken(String login) {
        return JWT.create()
                .withSubject(login) // sujeito a quem se refere o token (email do usuário)
                .withExpiresAt(// tempo de expirar o JWT
                        new Date(System.currentTimeMillis()+ expiration)) // System.currentTimeMillis() gera a data atual. + expiration soma a data atual mais o tempo de expiração, que é um dia aqui.
                .sign(Algorithm.HMAC512(secret));
    }

    public String getLogin(String token){
        return JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(token)
                .getSubject();// subject é o email/login
    }

}
