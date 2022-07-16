package com.SoulCode.servicos.Controllers;

import com.SoulCode.servicos.Models.Chamado;
import com.SoulCode.servicos.Models.Cliente;
import com.SoulCode.servicos.Models.Funcionario;
import com.SoulCode.servicos.Models.Pagamento;
import com.SoulCode.servicos.Services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class ChamadoController {

    @Autowired
    ChamadoService chamadoService;

    @GetMapping("/chamados")
    public List<Chamado> mostrarTodosOsChamados() {
        List<Chamado> chamado = chamadoService.mostrarTodosOsChamadosService();
        return chamado;
    }

    @GetMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> mostrarChamadoPeloIdService(@PathVariable Integer idChamado) {
        Chamado chamado = chamadoService.mostrarChamadoPeloIdService(idChamado);
        return ResponseEntity.ok().body(chamado);
    }

    @GetMapping("/chamadosPeloCliente/{idCliente}")
    public List<Chamado> buscarChamadosPeloCliente(@PathVariable Integer idCliente) {
        List<Chamado> chamado = chamadoService.buscarChamadosPeloClienteService(idCliente);
        return chamado;
    }

    @GetMapping("/chamadosPeloFuncionario/{idFuncionario}")
    public List<Chamado> buscarChamadosPeloFuncionario(@PathVariable Integer idFuncionario) {
        List<Chamado> chamado = chamadoService.buscarChamadosPeloIdDoFuncionarioService(idFuncionario);
        return chamado;
    }

    @GetMapping("/chamadosPeloStatus")
    public List<Chamado> buscarChamadosPeloStatus(@RequestParam("status") String status) {
        List<Chamado> chamado = chamadoService.buscarChamadosPeloStatusService(status);
        return chamado;
    }

    @GetMapping("/chamadosPorIntervaloData")
    public List<Chamado> buscarIntervaloDeData(@RequestParam("data1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data1,
                                               @RequestParam("data2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data2) {
        List<Chamado> chamados = chamadoService.buscarIntervaloDeDataService(data1, data2);
        return chamados;
    }

    // Aqui vmamos definir o endPoint para o serviço de cadastro de um novo chamado
    // Para cadastro precisamos anotar como método http - post

    @PostMapping("/chamados/{idCliente}")
    public ResponseEntity<Chamado> cadastrarChamado(@PathVariable Integer idCliente,
                                                    @RequestBody Chamado chamado) {
        chamado = chamadoService.cadastrarChamadoService(chamado, idCliente);
        // Nesse momento o chamado já foi cadastrado no database
        // Precisamos agora criar o caminho (URI) para que esse novo chamado possa ser acessado.
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(chamado.getIdChamado()).toUri();
        return ResponseEntity.created(novaUri).body(chamado);
    }

    // Vamos mapear o serviço de excluir um chamado
    @DeleteMapping("/chamados/{idChamado}")
    public ResponseEntity<Void> excluirChamado(@PathVariable Integer idChamado) {
        chamadoService.excluirChamadoService(idChamado);
        return ResponseEntity.noContent().build();
    }

    // Vamos mapear o serviço de editar um chamado
    // para edição precisamos do método http do tipo put

    @PutMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> editarChamado(@PathVariable Integer idChamado,
                                                 @RequestBody Chamado chamado) {
        chamado.setIdChamado(idChamado);
        chamado = chamadoService.editarChamadoService(chamado, idChamado);
        return ResponseEntity.ok().body(chamado);
    }

    // Vamos fazer o mapeamento do método de atribuir um funcionário a um determinado chamado

    @PutMapping("/chamadosAtribuirFuncionario/{idChamado}/{idFuncionario}")
    public ResponseEntity<Chamado> atribuirFuncionario(@PathVariable Integer idChamado,
                                                       @PathVariable Integer idFuncionario) {
        chamadoService.atribuirFuncionarioService(idChamado, idFuncionario);
        return ResponseEntity.noContent().build();
    }

    // Vamos construir o mapeamento do método para modificar o status de um chamado.
    @PutMapping("/chamadosModificarStatus/{idChamado}")
    public ResponseEntity<Chamado> modificarStatus(@PathVariable Integer idChamado,
                                                   @RequestParam("status") String status) {

        Chamado chamado = chamadoService.modificarStatusService(idChamado,status);
            return ResponseEntity.ok().body(chamado);
        }
    }

































