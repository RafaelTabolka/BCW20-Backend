package com.SoulCode.servicos.Services;

import com.SoulCode.servicos.Models.User;
import com.SoulCode.servicos.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Cacheable("cacheUser")
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    @CachePut(value = "cacheUser", key = "#user.id")
    public User cadastrarUsuario(User user) {
        return userRepository.save(user);
    }


}
