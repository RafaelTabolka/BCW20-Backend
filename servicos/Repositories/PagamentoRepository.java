package com.SoulCode.servicos.Repositories;

import com.SoulCode.servicos.Models.Chamado;
import com.SoulCode.servicos.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    @Query(value = "SELECT * FROM pagamento WHERE status =:status", nativeQuery = true)
    List<Pagamento> findPagamentoByStatus(String status);

    @Query(
            value = "SELECT pagamento.*, chamado.id_chamado, chamado.titulo, cliente.id, cliente.nome\n" +
                    "FROM chamado RIGHT JOIN pagamento \n" +
                    "ON chamado.id_chamado = pagamento.id_pagament\n" +
                    "LEFT JOIN cliente \n" +
                    "ON cliente.id = chamado.id_cliente", nativeQuery = true
    )
    List<List> findByPagamentoChamadoCliente();
}
