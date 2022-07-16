package com.SoulCode.servicos.Controllers;

import com.SoulCode.servicos.Models.Cliente;
import com.SoulCode.servicos.Models.Endereco;
import com.SoulCode.servicos.Services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @GetMapping("/enderecos")
    public List<Endereco> mostrarTodosOsEnderecos() {
        return enderecoService.mostrarTodosOsEnderecos();
    }

    @GetMapping("/enderecosId/{idEndereco}")
    public ResponseEntity<Endereco> mostrarEnderecoPeloId(@PathVariable Integer idEndereco) {
        Endereco endereco = enderecoService.mostrarEnderecoPeloId(idEndereco);
        return ResponseEntity.ok().body(endereco);
    }

    @DeleteMapping("/enderecos/{idEndereco}")
    public ResponseEntity<Void> excluirEndereco(@PathVariable Integer idEndereco) {
        enderecoService.excluirEndereco(idEndereco);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/enderecos/{idEndereco}")
    public ResponseEntity<Endereco> editarEndereco(@RequestBody Endereco endereco,
                                                   @PathVariable Integer idEndereco) {
        enderecoService.editarEndereco(endereco, idEndereco);
        return ResponseEntity.ok().body(endereco);
    }

    @GetMapping("/enderecoCidade/{cidade}")
    public List<Endereco> buscarPorCidade(@PathVariable String cidade) {
        List<Endereco> endereco = enderecoService.buscarPorCidade(cidade);
        return endereco;
    }

    @PostMapping("enderecos/{idCliente}")
    public ResponseEntity<Endereco> cadastrarEndereco(@PathVariable Integer idCliente,
                                                      @RequestBody Endereco endereco) throws Exception {

        try {
            endereco = enderecoService.cadastrarEndereco(endereco, idCliente);
            URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id")
                    .buildAndExpand(endereco.getId()).toUri();
            return ResponseEntity.created(novaUri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
































