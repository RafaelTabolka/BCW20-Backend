package com.SoulCode.servicos.Services;

import com.SoulCode.servicos.Models.Cargo;
import com.SoulCode.servicos.Models.Funcionario;
import com.SoulCode.servicos.Repositories.CargoRepository;
import com.SoulCode.servicos.Repositories.ChamadoRepository;
import com.SoulCode.servicos.Repositories.FuncionarioRepository;
import com.SoulCode.servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    CargoRepository cargoRepository;


// Primeiro serviço na tabela de funcionários vai ser a leitura de todos os funcionários cadastrados.
// FindAll -> método do Spring DATA JPA -> busca todos os registros de uma tabela

    public List<Funcionario> mostrarTodosFuncionarios(){
        return funcionarioRepository.findAll();
    }

    // Criar mais um serviço relacionado ao funcionáio
    // Criar um serviço de buscar apenas um funcionário pelo seu Id(chave primária)

    public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow(
                () -> new EntityNotFoundException("Funcionário não encontrado " + idFuncionario)
        ); // orElseThrow (ou se não lance uma exceção)
    }

    // Vamos criar mais um serviço para buscar um funcionário pelo seu email.

    public Funcionario mostrarUmFuncionarioPeloEmail(String email) {
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
        return funcionario.orElseThrow();
    }

    // Vamos criar um serviço para cadastrar um novo funcionário
    public Funcionario cadastrarFuncionario(Funcionario cadastroFuncionario, Integer idCargo) {
        cadastroFuncionario.setIdFuncionario(null);
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        cadastroFuncionario.setCargo(cargo.get());
        return funcionarioRepository.save(cadastroFuncionario);

        // Só por precaução, nós vamos pôr o Id do Funcionário como null.
    }

    public void excluirFuncionario(Integer idFuncionario) {
        funcionarioRepository.deleteById(idFuncionario);
    }

    public Funcionario editarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto) {
        Funcionario funcionario = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atribuirCargo(Integer idCargo, Funcionario funcionario) {
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        funcionario.setCargo(cargo.get());
       return funcionarioRepository.save(funcionario);
    }
    //pegar id do cargo
    // Buscar cargo na tabela de cargo
    // id do cargo findById


    public List<Funcionario> cargosDosFuncionarios(Integer idCargo){
    return funcionarioRepository.findByFuncionarioWhereIdCargo(idCargo);
    }



}






























