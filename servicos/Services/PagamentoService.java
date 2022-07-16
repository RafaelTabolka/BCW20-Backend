package com.SoulCode.servicos.Services;

import com.SoulCode.servicos.Models.Chamado;
import com.SoulCode.servicos.Models.Pagamento;
import com.SoulCode.servicos.Models.StatusPagamento;
import com.SoulCode.servicos.Repositories.ChamadoRepository;
import com.SoulCode.servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;

    public List<Pagamento> buscarTodosOsPagamentos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento buscarPagamentoPeloId(Integer idPagamento) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }

    public Pagamento cadastrarPagamento(Pagamento pagamento, Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        if (chamado.isPresent()) {
            pagamento.setIdPagament(idChamado);
            pagamento.setStatus(StatusPagamento.LANCADO);
            pagamentoRepository.save(pagamento);


            chamado.get().setPagamento(pagamento);
            chamadoRepository.save(chamado.get());

            return pagamento;
        }
        else {
            throw new RuntimeException();
        }
    }

    public Pagamento editarPagamento(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento editarStatusPagamento(Integer idPagamento, String status) {
        Pagamento pagamento = buscarPagamentoPeloId(idPagamento);

        switch (status){
            case "QUITADO":
                pagamento.setStatus(StatusPagamento.QUITADO);
                break;
            case "LANCADO":
                pagamento.setStatus(StatusPagamento.LANCADO);
                break;
        }
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> buscarPagamentoPeloStatus(String status) {
        List<Pagamento> pagamento = pagamentoRepository.findPagamentoByStatus(status);

        return pagamento;
    }

    public List<List> buscarChamadoClientePagamento() {
        return pagamentoRepository.findByPagamentoChamadoCliente();
    }

}
