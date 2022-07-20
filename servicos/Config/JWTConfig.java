package com.SoulCode.servicos.Config;

import com.SoulCode.servicos.Security.JWTAuthenticationFilter;
import com.SoulCode.servicos.Security.JWTAutorizationFilter;
import com.SoulCode.servicos.Services.AuthUserDetailService;
import com.SoulCode.servicos.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// Agrega todas as informações de segurança http e gerência do user
@EnableWebSecurity
public class JWTConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthUserDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Usa passwordEncoder() para comparar senhas de login
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }


    // Função que configura a segurança dio http
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // habilita o cors e desabilita o csrf (proteção para ataque do tipo csrf)
        http.cors().and().csrf().disable();// disable, desabilita a proteção

        // Filtro de autenticação. JWTAuthenticationFilter é chamado quando usa /login
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtils));
        http.addFilter((new JWTAutorizationFilter(authenticationManager(), jwtUtils)));
        http.authorizeRequests() // começa a autorizar as requisições
                .antMatchers(HttpMethod.POST, "/login").permitAll() // login é público para todos, pode ser passado outro antMatchers, com outra rota e essa rota será acessível a todos
                // Exemplo de outra autorização: .antMatchers(HttpMethod.GET, "/servicos/**", asterísco representa "qualquer coisa"
                //.permitAll()
                .anyRequest().authenticated(); // qualquer outra que não seja /login será não acessível a quem não é logado

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // maneira que vai gerenciar a seção. Stateless não vai armazenar informação de token, vai ter que mandar novamente sempre.
    }

    @Bean // cross origin resource sharing
    CorsConfigurationSource corsConfigurationSource() { // disponibiliza uma configuração global para não haver erro de CORS
        CorsConfiguration configuration = new CorsConfiguration(); // configurações padrões
        configuration.setAllowedMethods(List.of( // quais métodos do http estão liberados via CORS
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        )); // métodos permitidos para o front acessar
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // endpoints permitidos para o front acessar
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;

        //  "/servicos/funcionarios" -> "/**" asterísco é considerado qualquer endpoint, todos os endpoints são liberados para os métodos acima
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}































