package com.SoulCode.servicos.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Abstrai o user do banco para que o security conheça seus dados
public class AuthUserDetail implements UserDetails {

    private String login;
    private String password;

    public AuthUserDetail(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(); // Como não há autoridades do usuário na aplicação, será retornado vazio
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // a conta não expirou

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // a conta não bloqueou

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // as credenciais não expiraram

    @Override
    public boolean isEnabled() {
        return true;
    } // o usuário está habilitado


    /*
     * O Spring Security não se comunica diretamente com o nosso model User =(
     * Então devemos criar uma classe que ele conheça para fazer essa comunicação,
     * UserDetails = Guarda informações do contexto de autenticação do usuário (autorizações, habilitado, etc)
     * */
}




































