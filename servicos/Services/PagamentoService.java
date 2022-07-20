package com.SoulCode.servicos.Services;

import com.SoulCode.servicos.Models.Chamado;
import com.SoulCode.servicos.Models.Pagamento;
import com.SoulCode.servicos.Models.StatusPagamento;
import com.SoulCode.servicos.Repositories.ChamadoRepository;
import com.SoulCode.servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;

    @Cacheable("cachePagamento")
    public List<Pagamento> buscarTodosOsPagamentos() {
        return pagamentoRepository.findAll();
    }

    @Cacheable(value = "cachePagamento", key = "#idPagament")
    public Pagamento buscarPagamentoPeloId(Integer idPagament) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagament);
        return pagamento.orElseThrow();
    }

    @CachePut(value = "cachePagamento", key = "#idChamado")
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

    @CachePut(value = "cachePagamento", key = "#pagamento.idPagament")
    public Pagamento editarPagamento(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    @CachePut(value = "cachePagamento", key = "#idPagament")
    public Pagamento editarStatusPagamento(Integer idPagament, String status) {
        Pagamento pagamento = buscarPagamentoPeloId(idPagament);

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

    @Cacheable(value = "cachePagamento", key = "#status")
    public List<Pagamento> buscarPagamentoPeloStatus(String status) {
        List<Pagamento> pagamento = pagamentoRepository.findPagamentoByStatus(status);

        return pagamento;
    }

    @Cacheable("cachePagamentos")
    public List<List> buscarChamadoClientePagamento() {
        return pagamentoRepository.findByPagamentoChamadoCliente();
    }
}
