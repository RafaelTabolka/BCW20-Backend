package com.SoulCode.servicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableCaching // essa anotação diz que a aplicação agora pode ser usado o cache
@SpringBootApplication
public class ServicosApplication {

	public static void main(String[] args) {SpringApplication.run(ServicosApplication.class, args);

//		System.out.println(new BCryptPasswordEncoder().encode("batata"));

		System.out.println("Hello, world!");

	}
}
