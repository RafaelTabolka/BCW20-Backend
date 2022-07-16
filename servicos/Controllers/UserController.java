package com.SoulCode.servicos.Controllers;

import com.SoulCode.servicos.Models.User;
import com.SoulCode.servicos.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public List<User> listarUsuarios() {
        return userService.listarUsuarios();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<User> cadastrarUsuario(@RequestBody User user) {
        String senhaCodificada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCodificada);
        user = userService.cadastrarUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
