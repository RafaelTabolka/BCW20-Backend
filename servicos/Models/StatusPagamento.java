package com.SoulCode.servicos.Models;

public enum StatusPagamento {

    QUITADO("Dívida quitada"),
    LANCADO("Não finalizado");


    private String pagamento;

    StatusPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }
}
