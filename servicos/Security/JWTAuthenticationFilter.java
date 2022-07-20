package com.SoulCode.servicos.Security;


import com.SoulCode.servicos.Controllers.Exceptions.ResourceExceptionHandler;
import com.SoulCode.servicos.Models.User;
import com.SoulCode.servicos.Util.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

// essa classe entra em ação ao chamar o /login
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTUtils jwtUtils;


    public JWTAuthenticationFilter(AuthenticationManager manager, JWTUtils jwtUtils){
        this.authenticationManager = manager;
        this.jwtUtils = jwtUtils;
    }

    //HttpServletRequest e response seriam as requisições e respostas de maneira bruta, sem a filtragem como no controller
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // Tenta autenticar o usuário
        try{
            // Na linha abaixo está extraindo as informações do usuário da request "bruta" e transformando naquele "requestBody" como no controller
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            // abaixo está a autenticação do spring
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(),
                            user.getPassword(),
                            new ArrayList<>())); // autoridades do usuário, como não tem aqui é passado um array vazio
        }catch(IOException e) { // caso a leitura da linha 40 falhe, o json da requisição não baser com a classe user, tem o tratamento da exceção
            throw new RuntimeException(e.getCause());
        }
    }


    // Gerar o token e devolver para o usuário que se autenticou com sucesso
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AuthUserDetail user = (AuthUserDetail) authResult.getPrincipal();
        String token = jwtUtils.generateToken(user.getUsername()); // gera o token com o username

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        // {"Authorization": "<token>"}
        response.getWriter().write("{\"Authorization\":\"" + token + "\"}"); // escreve no body
        response.getWriter().flush(); // flush fecha a resposta
    }

    // Quando der errado a autenticação
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // customizar a respoista de erro (login que falhou)
        response.setStatus(401); // unauthorized (usuário não autorizado)
        response.setContentType("aplication/json");
        response.getWriter().write(json()); // mensagem de erro no body
        response.getWriter().flush();// termina a escrita
    }


    String json() { // formatar a mensagen de erro
    long date = new Date().getTime();
    return "{" +
            "\"timestamp\":" + date + ", " +
            "\"status\": 401, " +
            "\"error\" : \"Não autorizado\", " +
            "\"message\": \"Email/senha inválida\"," +
            "\"path\":\"/login\"" +
            "}";
    }
}
/**
 * FRONT MANDA {"login": "jr@gmail.com", "password": "12345"}
 * A partir do JSON -> User
 * Tenta realizar autenticação
 *      Caso dê certo:
 *          - Gera o token JWT
 *          - Retorna o token para o FRONT
 */