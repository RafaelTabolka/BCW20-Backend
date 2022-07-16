package com.SoulCode.servicos.Repositories;

import com.SoulCode.servicos.Models.Chamado;
import com.SoulCode.servicos.Models.Cliente;
import com.SoulCode.servicos.Models.Funcionario;
import com.SoulCode.servicos.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository <Chamado, Integer> {

    List<Chamado> findByCliente(Optional<Cliente> cliente);
    List<Chamado> findByFuncionario(Optional<Funcionario> funcionario);

    @Query(value = "SELECT * FROM chamado WHERE status =:status", nativeQuery = true)
    List<Chamado> findByStatus (String status);

    @Query(value = "SELECT * FROM chamado WHERE data_entrada BETWEEN :date1 AND :date2", nativeQuery = true)
    List<Chamado> finndByIntervaloData (Date date1, Date date2);

}
