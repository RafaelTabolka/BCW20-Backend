package com.SoulCode.servicos.Repositories;

import com.SoulCode.servicos.Models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository <Endereco, Integer>{

    List<Endereco> findByCidade(String cidade);

}
