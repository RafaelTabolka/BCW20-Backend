package com.SoulCode.servicos.Repositories;

import com.SoulCode.servicos.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository <User, Integer> {

    public Optional<User> findByLogin(String login); // Irá buscar pelo email do user
}
