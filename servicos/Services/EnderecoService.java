package com.SoulCode.servicos.Services;

import com.SoulCode.servicos.Models.Cliente;
import com.SoulCode.servicos.Models.Endereco;
import com.SoulCode.servicos.Repositories.ClienteRepository;
import com.SoulCode.servicos.Repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteService clienteService;

    public List<Endereco> mostrarTodosOsEnderecos() {
        return enderecoRepository.findAll();
    }

    public Endereco mostrarEnderecoPeloId(Integer idEndereco) {
        Optional<Endereco> endereco = enderecoRepository.findById(idEndereco);
        return endereco.orElseThrow();
    }

    public void excluirEndereco(Integer idEndereco) {
        enderecoRepository.deleteById(idEndereco);
    }

    public Endereco editarEndereco(Endereco endereco, Integer idEndereco) { // Tem que passar o id do enderereço para uma segurança a mais
        endereco.setId(idEndereco);
        return enderecoRepository.save(endereco);

        //Save procura se o endereço existe, se extistir ele edita, se não existir ele cria um novo endereço
    }

    public List<Endereco> buscarPorCidade(String cidade) {
        List<Endereco> endereco = enderecoRepository.findByCidade(cidade);
        return endereco;
    }

    // Regras 1 -> Para cadastrar um endereço, o cliente já deve estar cadastrado do database
    // 2 -> No momento do cadastro do endereço, precisamos passar o id do cliente dono desse endereço
    // 3 -> O id do endereço vai ser o mesmo id do cliente
    // 4 -> Não permitir que um endereço seja salvo sem a existência do respectivo cliente
    public Endereco cadastrarEndereco(Endereco endereco, Integer idCliente) throws Exception {
    // Estamos declarando um optional de cliente e atribuindo para este os dados do cliente que receberá o novo endereço
        Optional <Cliente> cliente = clienteRepository.findById(idCliente);
//        if(cliente.get().getId() != null) {
        if(cliente.isPresent()) {
            endereco.setId(idCliente);
            enderecoRepository.save(endereco);

            cliente.get().setEndereco(endereco);
            clienteRepository.save(cliente.get());
            return endereco;

        } else {
            throw new Exception();
            // Poderia ser feito com runTimeException e não teria de fazer essas alterações.
        }
    }


}































