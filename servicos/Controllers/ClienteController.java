package com.SoulCode.servicos.Controllers;

import com.SoulCode.servicos.Models.Chamado;
import com.SoulCode.servicos.Models.Cliente;
import com.SoulCode.servicos.Models.Endereco;
import com.SoulCode.servicos.Models.Pagamento;
import com.SoulCode.servicos.Services.ClienteService;
import com.SoulCode.servicos.Services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping ("servicos")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    EnderecoService enderecoService;

    @GetMapping("/mostrarTodos")
    public List<Cliente> mostrarTodosOsClientes(){
        List<Cliente> clientes = clienteService.mostrarTodosOsClientesService(); // é aqui que ocorre a pergunta para o redis se existe os valores em cahce ou não
        // caso não tenha os valores em cache, será requisitado ao banco, o banco passa para o spring, o spring passa para o redis e o redis passa para o spring e o spring para o usuário
        return clientes;
    }

    @GetMapping("/mostrarTodos/{idCliente}")
    public ResponseEntity <Cliente> mostrarClientePeloId(@PathVariable Integer idCliente){
        Cliente cliente = clienteService.mostrarClientePeloIdService(idCliente);
        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping("/mostrar/{email}")
    public ResponseEntity<Cliente> mostrarClientePeloEmail(@PathVariable String email){
        Cliente cliente = clienteService.mostrarClientePeloEmailService(email);
        return ResponseEntity.ok().body(cliente);
    }

    // Modificado para receber o idEndereco como segundo argumento passado na Url, cadastrando o cliente já com o endereço
    @PostMapping("/mostrarTodos")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        cliente = clienteService.cadastrarClienteService(cliente);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(novaUri).body(cliente);
    }

    @DeleteMapping("/mostrarTodos/{idCliente}")
    public ResponseEntity<Void> excluirCliente (@PathVariable Integer idCliente){
        clienteService.excluirClienteService(idCliente);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/mostrarTodos/{idCliente}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Integer idCliente,
                                                 @RequestBody Cliente cliente) {
        cliente.setId(idCliente);
        clienteService.editarClienteService(cliente);
        return ResponseEntity.noContent().build();
    }
}















