package com.SoulCode.servicos.Repositories;

import com.SoulCode.servicos.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional <Cliente> findByEmail(String email);

}
