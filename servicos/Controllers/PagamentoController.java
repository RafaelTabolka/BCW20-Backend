package com.SoulCode.servicos.Controllers;

import com.SoulCode.servicos.Models.Pagamento;
import com.SoulCode.servicos.Services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("servicos")
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @GetMapping("/pagamentos")
    public List<Pagamento> buscarPorTodosOsPagamentos() {
        return pagamentoService.buscarTodosOsPagamentos();
    }

    @GetMapping("/pagamentos/{idPagamento}")
    public Pagamento buscarPagamentoPeloId(@PathVariable Integer idPagamento) {
        Pagamento pagamento = pagamentoService.buscarPagamentoPeloId(idPagamento);
        return pagamento;
    }

    @GetMapping("/pagamentosJuncao")
    public List<List> buscarChamadoClientePagamento() {
        List<List> pagamentos = pagamentoService.buscarChamadoClientePagamento();
        return pagamentos;
    }

    @GetMapping("/pagamentosStatus")
    public List<Pagamento> buscarPagamentoPeloStatus(@RequestParam ("status") String status) {
        List<Pagamento>pagamento =  pagamentoService.buscarPagamentoPeloStatus(status);
        return pagamento;
    }



    @PostMapping("/pagamentos/{idChamado}")
    public ResponseEntity<Pagamento> cadastrarPagamento(@PathVariable Integer idChamado,
                                                         @RequestBody Pagamento pagamento) {
        pagamento = pagamentoService.cadastrarPagamento(pagamento, idChamado);

        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(pagamento.getIdPagament()).toUri();

        return ResponseEntity.created(novaUri).build();
    }

    @PutMapping("/pagamentos/{idPagamento}")
    public ResponseEntity<Pagamento> editarPagamento(@RequestBody Pagamento pagamento,
                                                     @PathVariable Integer idPagamento) {
        pagamento.setIdPagament(idPagamento);
        pagamentoService.editarPagamento(pagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @PutMapping("/pagamentosStatus/{idPagamento}")
    public ResponseEntity<Pagamento> editarStatusPagamento(@PathVariable Integer idPagamento,
                                                           @RequestParam ("status") String status){
        Pagamento pagamento = pagamentoService.editarStatusPagamento(idPagamento,status);
        return ResponseEntity.ok().body(pagamento);
    }
}















