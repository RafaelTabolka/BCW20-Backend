package com.SoulCode.servicos.Repositories;

import com.SoulCode.servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findByEmail(String email);
    // Optional<Funcionario> findByNome(String nome); outro exemplo de pesquisa
    // Optional<Funcionario> findByNomeAndEmail(String nome, String email);

    @Query (value = "SELECT * FROM funcionario WHERE id_cargo =:idCargo", nativeQuery = true)
    List<Funcionario> findByFuncionarioWhereIdCargo(Integer idCargo);
}
