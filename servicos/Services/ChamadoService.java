package com.SoulCode.servicos.Services;

import ch.qos.logback.core.net.server.Client;
import com.SoulCode.servicos.Models.*;
import com.SoulCode.servicos.Repositories.ChamadoRepository;
import com.SoulCode.servicos.Repositories.ClienteRepository;
import com.SoulCode.servicos.Repositories.FuncionarioRepository;
import com.SoulCode.servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    ChamadoRepository chamadoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Cacheable("cacheChamado")
    public List<Chamado> mostrarTodosOsChamadosService() {
        return chamadoRepository.findAll();
    }

    @Cacheable(value = "cacheChamado", key = "#idChamado")
    public Chamado mostrarChamadoPeloIdService(Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        return chamado.orElseThrow();
    }

    @Cacheable(value = "cacheChamado", key = "#id")
    public List<Chamado> buscarChamadosPeloClienteService(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return chamadoRepository.findByCliente(cliente);
    }

    @Cacheable(value = "cacheChamado", key = "#idFuncionario")
    public List<Chamado> buscarChamadosPeloIdDoFuncionarioService(Integer idFuncionario) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }

    @Cacheable(value = "cacheChamado", key = "#status")
    public List<Chamado> buscarChamadosPeloStatusService(String status) {
        return chamadoRepository.findByStatus(status);
    }

    @Cacheable("cacheChamadoData")
    public List<Chamado> buscarIntervaloDeDataService(Date data1, Date data2) {
        return chamadoRepository.finndByIntervaloData(data1, data2);
    }

    // Servi??o para cadastrar um novo chamado
    // temos 3 regras para cadastro de servi??os nessa empresa
    // 1?? No momento do cadastro do chamado, j?? devemos informar de qual cliente ??
    // 2?? No momento do cadastro do chamado, a princ??pio vamos fazer esse cadastro sem estar atribu??do a um funcion??rio.
    // 3?? No momento do cadastro do chamado, o status do mesmo deve ser RECEBIDO

    @CachePut(value = "cacheChamado", key = "#id")
    public Chamado cadastrarChamadoService(Chamado chamado, Integer id) {
        chamado.setStatus(StatusChamado.RECEBIDO);// Regra 3 - atribui????o do status recebido para o chamado que est?? sendo cadastrado
        chamado.setFuncionario(null); // Regra 2 - dizer que ainda n??o atribu??mos esse chamado para nenhum funcion??rio
        Optional<Cliente> cliente = clienteRepository.findById(id); // Regra 1 - buscando os dados do cliente dono do chamado
        chamado.setCliente(cliente.get());
        return chamadoRepository.save(chamado);
    }

    // M??todo para exclus??o de um chamado

    @CacheEvict(value = "cacheChamado", key = "id", allEntries = true)
    public void excluirChamadoService(Integer id) {
        chamadoRepository.deleteById(id);
    }

    // M??todo para editar um chamado
    // No momento da edi????o de um chamado, devemos preservar o cliente e o funcion??rio desse chamado.
    // Vamos editar os dados do chamado, mas continuamos com os dados do cliente e os dados do funcion??rio.

    @CachePut(value = "cacheChamado", key = "#id")
    public Chamado editarChamadoService(Chamado chamado, Integer id) {
        // Instanciamos aqui um objeto do tipo Chamado para guardar os dados do chamado sem as novas altera????es.
        Chamado chamadoSemAsNovasAlteracoes = mostrarChamadoPeloIdService(id);
        Funcionario funcionario = chamadoSemAsNovasAlteracoes.getFuncionario();
        Cliente cliente = chamadoSemAsNovasAlteracoes.getCliente();

        chamado.setCliente(cliente);
        chamado.setFuncionario(funcionario);

        return chamadoRepository.save(chamado);
    }

    // M??todo para atribuir um funcion??rio para um determinado chamado
    // ou trocar o funcion??rio de determinado chamado
    // Regra: no momento em que um determinado chamado ?? atribuido a um funcionario,
    // o status do chamado precisa ser alterado para ATRIBUIDO

    @CachePut(value = "cacheChamado", key = "idFuncionario")
    public Chamado atribuirFuncionarioService(Integer idChamado, Integer idFuncionario) {
        // Buscar os dados do funcionario que vai ser atribu??do a esse chamado.
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);

        // Buscar o chamado para o qual vai ser esoecificado o funcion??rio escolhido
        Chamado chamado = mostrarChamadoPeloIdService(idChamado);
        chamado.setFuncionario(funcionario.get());
        chamado.setStatus(StatusChamado.ATRIBUIDO);

        return chamadoRepository.save(chamado);
    }

    // M??todo para modificar o status de um chamado.

    @CachePut(value = "cacheChamado", key = "#id")
    public Chamado modificarStatusService(Integer id, String status) {
        Chamado chamado = mostrarChamadoPeloIdService(id);

        if (chamado.getFuncionario() != null) {
            switch (status) {
                case "ATRIBUIDO":
                    chamado.setStatus(StatusChamado.ATRIBUIDO);
                    break;

                case "CONCLUIDO":
                    chamado.setStatus(StatusChamado.CONCLUIDO);
                    break;

                case "ARQUIVADO":
                    chamado.setStatus(StatusChamado.ARQUIVADO);
                    break;
            }
        }
        switch (status) {
            case "RECEBIDO":
                chamado.setStatus(StatusChamado.RECEBIDO);
                break;
        }
        return chamadoRepository.save(chamado);
    }


}























