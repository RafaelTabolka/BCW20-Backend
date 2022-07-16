package com.SoulCode.servicos.Services;

import ch.qos.logback.core.net.server.Client;
import com.SoulCode.servicos.Models.*;
import com.SoulCode.servicos.Repositories.ChamadoRepository;
import com.SoulCode.servicos.Repositories.ClienteRepository;
import com.SoulCode.servicos.Repositories.FuncionarioRepository;
import com.SoulCode.servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Chamado> mostrarTodosOsChamadosService() {
        return chamadoRepository.findAll();
    }

    public Chamado mostrarChamadoPeloIdService(Integer id) {
        Optional<Chamado> chamado = chamadoRepository.findById(id);
        return chamado.orElseThrow();
    }

    public List<Chamado> buscarChamadosPeloClienteService(Integer idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return chamadoRepository.findByCliente(cliente);
    }

    public List<Chamado> buscarChamadosPeloIdDoFuncionarioService(Integer idFuncionario) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }

    public List<Chamado> buscarChamadosPeloStatusService(String status) {
        return chamadoRepository.findByStatus(status);
    }

    public List<Chamado> buscarIntervaloDeDataService(Date data1, Date data2) {
        return chamadoRepository.finndByIntervaloData(data1, data2);
    }

    // Serviço para cadastrar um novo chamado
    // temos 3 regras para cadastro de serviços nessa empresa
    // 1º No momento do cadastro do chamado, já devemos informar de qual cliente é
    // 2º No momento do cadastro do chamado, a princípio vamos fazer esse cadastro sem estar atribuído a um funcionário.
    // 3º No momento do cadastro do chamado, o status do mesmo deve ser RECEBIDO

    public Chamado cadastrarChamadoService(Chamado chamado, Integer idCliente) {
        chamado.setStatus(StatusChamado.RECEBIDO);// Regra 3 - atribuição do status recebido para o chamado que está sendo cadastrado
        chamado.setFuncionario(null); // Regra 2 - dizer que ainda não atribuímos esse chamado para nenhum funcionário
        Optional<Cliente> cliente = clienteRepository.findById(idCliente); // Regra 1 - buscando os dados do cliente dono do chamado
        chamado.setCliente(cliente.get());
        return chamadoRepository.save(chamado);
    }

    // Método para exclusão de um chamado

    public void excluirChamadoService(Integer idChamado) {
        chamadoRepository.deleteById(idChamado);
    }

    // Método para editar um chamado
    // No momento da edição de um chamado, devemos preservar o cliente e o funcionário desse chamado.
    // Vamos editar os dados do chamado, mas continuamos com os dados do cliente e os dados do funcionário.

    public Chamado editarChamadoService(Chamado chamado, Integer idChamado) {
        // Instanciamos aqui um objeto do tipo Chamado para guardar os dados do chamado sem as novas alterações.
        Chamado chamadoSemAsNovasAlteracoes = mostrarChamadoPeloIdService(idChamado);
        Funcionario funcionario = chamadoSemAsNovasAlteracoes.getFuncionario();
        Cliente cliente = chamadoSemAsNovasAlteracoes.getCliente();

        chamado.setCliente(cliente);
        chamado.setFuncionario(funcionario);

        return chamadoRepository.save(chamado);
    }

    // Método para atribuir um funcionário para um determinado chamado
    // ou trocar o funcionário de determinado chamado
    // Regra: no momento em que um determinado chamado é atribuido a um funcionario,
    // o status do chamado precisa ser alterado para ATRIBUIDO

    public Chamado atribuirFuncionarioService(Integer idChamado, Integer idFuncionario) {
        // Buscar os dados do funcionario que vai ser atribuído a esse chamado.
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);

        // Buscar o chamado para o qual vai ser esoecificado o funcionário escolhido
        Chamado chamado = mostrarChamadoPeloIdService(idChamado);
        chamado.setFuncionario(funcionario.get());
        chamado.setStatus(StatusChamado.ATRIBUIDO);

        return chamadoRepository.save(chamado);
    }

    // Método para modificar o status de um chamado.

    public Chamado modificarStatusService(Integer idChamado, String status) {
        Chamado chamado = mostrarChamadoPeloIdService(idChamado);

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























