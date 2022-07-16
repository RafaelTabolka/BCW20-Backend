package com.SoulCode.servicos.Services;

import com.SoulCode.servicos.Models.Cliente;
import com.SoulCode.servicos.Models.Endereco;
import com.SoulCode.servicos.Models.Funcionario;
import com.SoulCode.servicos.Repositories.ClienteRepository;
import com.SoulCode.servicos.Repositories.EnderecoRepository;
import com.SoulCode.servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Cacheable("clientesCache") // Só chamo o return se o cache expirar. Cache clientesCache:: []
    public List<Cliente> mostrarTodosOsClientesService() {
        return clienteRepository.findAll();
    }

    @Cacheable(value = "clientesCache", key = "#idCliente") // clientesCache::1
    public Cliente mostrarClientePeloIdService(Integer idCliente) {
        Optional<Cliente> clientes = clienteRepository.findById(idCliente);
        return clientes.orElseThrow(
                () -> new EntityNotFoundException("Cliente não encontrado - Id: " + idCliente)
        );
    }

    public Cliente mostrarClientePeloEmailService(String email) {
        Optional<Cliente> clientes = clienteRepository.findByEmail(email);
        return clientes.orElseThrow();
    }

    // Modificado para receber o idEndereco como segundo argumento passado na Url, cadastrando o cliente já com o endereço
    public Cliente cadastrarClienteService(Cliente cadastroCliente) {
        cadastroCliente.setId(null);
       return clienteRepository.save(cadastroCliente);
    }

    @CacheEvict(value = "clientesCache", key = "#idCliente", allEntries = true)
    public void excluirClienteService(Integer idCliente) {
        clienteRepository.deleteById(idCliente);
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente")
    public Cliente editarClienteService(Cliente cliente) {
        mostrarClientePeloIdService(cliente.getId());
        return clienteRepository.save(cliente);
    }
}

