package com.SoulCode.servicos.Security;

import com.SoulCode.servicos.Util.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

// Entra em ação em toda requisição, endpoint, que está protegida
public class JWTAutorizationFilter extends BasicAuthenticationFilter {

    private JWTUtils jwtUtils;
    public JWTAutorizationFilter(AuthenticationManager manager, JWTUtils jwtUtils) {
        super(manager);
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization"); // Bearer dados2313123
        if(token != null && token.startsWith("Bearer")){ // token "válido"
            // Concluir autorização
            UsernamePasswordAuthenticationToken authToken = getAuthentication(token.substring(7));
            // SecutiryContextHolder.getContext().getAuthentication(); salva a linha acima e segue normalmente com o doFilter abaixo
            // acima método do sping para autenticação do token
            if(authToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // Guarda informações do usuário autenticado no contexto do Sping
                // Essa informação pode ser utilizada dentro dos controllers da aplicação.
            }
        }

        chain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String login = jwtUtils.getLogin(token); // pega o token e extai o login do subject
        if(login == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(login, null, new ArrayList<>());
    }
}
