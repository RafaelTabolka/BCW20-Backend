package com.SoulCode.servicos.Controllers;

import com.SoulCode.servicos.Models.Funcionario;
import com.SoulCode.servicos.Services.FuncionarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin // Faz com que as aplicações que rodam em portas diferentes, possam acessar os end points.
@RestController // Classe responsável pela classe de controller significa que essa aplicação vai fazer a camada de controller. Camada onde estarão os end points
@RequestMapping("servicos")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping("/funcionarios")
    public List<Funcionario> mostrarTodosFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionarios();
        return funcionarios;
    }

    @GetMapping("/funcionarios/{idFuncionario}")
        public ResponseEntity <Funcionario> mostrarUmFuncionarioPeloId(@PathVariable Integer idFuncionario){
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloId(idFuncionario);
        return ResponseEntity.ok().body(funcionario);
    } //Não há necessidade de ser o mesmo nome da função do service

    @GetMapping("/funcionariosEmail/{email}")
    public ResponseEntity <Funcionario> mostrarUmFuncionarioPeloEmail(@PathVariable String email){
        Funcionario funcionarioemail = funcionarioService.mostrarUmFuncionarioPeloEmail(email);
        return ResponseEntity.ok().body(funcionarioemail);
    }

    @PostMapping("/funcionarios/{idCargo}")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario novoFuncionario,
                                                            @PathVariable Integer idCargo){
        // Na linha 43 o funcionário já é salvo na tabela do DataBase,
        // agora precisamos criar uma URI para esse novo registro da tabela.
        novoFuncionario = funcionarioService.cadastrarFuncionario(novoFuncionario, idCargo);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id")
                .buildAndExpand(novoFuncionario.getIdFuncionario()).toUri();
        return ResponseEntity.created(novaUri).body(novoFuncionario);
    }

    @DeleteMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Integer idFuncionario){
        funcionarioService.excluirFuncionario(idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable Integer idFuncionario, @RequestBody Funcionario funcionario){
        funcionario.setIdFuncionario(idFuncionario);
        funcionarioService.editarFuncionario(funcionario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cargoFuncionario/{idFuncionario}")
    public ResponseEntity<Funcionario> atribuirCargo(@PathVariable Integer idFuncionario,
                                                     @RequestParam ("idCargo") Integer idCargo,
                                                     @RequestBody Funcionario funcionario){
        funcionario.setIdFuncionario(idFuncionario);
        funcionarioService.atribuirCargo(idCargo,funcionario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/funcionariosPorCargo/{idCargo}")
    public List<Funcionario> cargosDosFuncionario(@PathVariable Integer idCargo) {
        List<Funcionario> funcionario = funcionarioService.cargosDosFuncionarios(idCargo);
        return funcionario;
    }

}
