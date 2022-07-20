package com.SoulCode.servicos.Services;

import com.SoulCode.servicos.Models.User;
import com.SoulCode.servicos.Repositories.UserRepository;
import com.SoulCode.servicos.Security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Cacheable(value = "authCache", key = "#login")
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user =  userRepository.findByLogin(login);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return new AuthUserDetail(user.get().getLogin(), user.get().getPassword());
/**
 * O propósito do UserDetailService é carregar de alguma fonte de dados
 * o usuário e criar uma instância de AuthUserDetail, conhecida pelo Spring.
 */
    }
}
